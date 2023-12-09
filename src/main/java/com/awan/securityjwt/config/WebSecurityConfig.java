package com.awan.securityjwt.config;

import com.awan.securityjwt.security.entrypoint.AuthenticationEntryPointImpl;
import com.awan.securityjwt.security.filter.JWTFilterUserLocal;
import com.awan.securityjwt.service.interfaces.UserLDAPService;
import com.awan.securityjwt.service.interfaces.UserLocalService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    /* Data Source */
    private final UserLocalService userLocalService;
    private final UserLDAPService userLDAPService;

    /* Handler */
    private final AuthenticationEntryPointImpl authenticationEntryPointImpl;

    /*` Filter */
    private final JWTFilterUserLocal jwtFilterUserLocal;


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userLocalService).passwordEncoder(passwordEncoder())
                .and()
                .userDetailsService(userLDAPService).passwordEncoder(passwordEncoder());
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        /* Disable CSRF */
        http.csrf().disable();

        /* Permit only api auth */
        http.authorizeRequests()
                .antMatchers("/api/auth").permitAll()
                .anyRequest().authenticated();

        // Handling AuthenticationExeption
        http.exceptionHandling()
                .authenticationEntryPoint(authenticationEntryPointImpl);

        // Register JWT Filter
        http.addFilterBefore(jwtFilterUserLocal, UsernamePasswordAuthenticationFilter.class);

        // No Session Save
        http.sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }
}

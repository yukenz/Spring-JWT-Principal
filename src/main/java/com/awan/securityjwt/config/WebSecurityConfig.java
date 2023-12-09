package com.awan.securityjwt.config;

import com.awan.securityjwt.security.entrypoint.JwtAuthenticationEntryPoint;
import com.awan.securityjwt.security.filter.JWTFilter;
import com.awan.securityjwt.service.interfaces.UserLDAPService;
import com.awan.securityjwt.service.interfaces.UserService;
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

    private final UserService userService;
    private final UserLDAPService userLDAPService;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JWTFilter jwtFilter;


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService).passwordEncoder(passwordEncoder())
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
                .authenticationEntryPoint(jwtAuthenticationEntryPoint);

        // Register JWT Filter
        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        // No Session Save
        http.sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }
}

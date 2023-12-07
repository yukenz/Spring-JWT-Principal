package com.awan.securityjwt.security.filter;

import com.awan.securityjwt.service.interfaces.JWTService;
import com.awan.securityjwt.service.interfaces.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

@Component
@RequiredArgsConstructor
@Slf4j
public class JWTFilter extends OncePerRequestFilter {

    private final JWTService jwtService;
    private final UserService userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        //Bisa gak dapetin username dari token bearer
        String authorization = request.getHeader("Authorization");
        String username = null;
        String jwtToken = null;

        if (Objects.nonNull(authorization) && authorization.startsWith("Bearer")) {

            try {
                jwtToken = authorization.substring(7);
                username = jwtService.getSubjectJWT(jwtToken);
                log.info("JWTFilter success set token & username");
            } catch (Exception e) {
                log.info("User Not Found");
            }
        }

        // username terisi tapi context null
        if (Objects.nonNull(username) && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = userService.loadUserByUsername(username);

            log.info("UserDetail Acquired");
            if (jwtService.validateJWT(jwtToken, userDetails)) {

                UsernamePasswordAuthenticationToken authObj = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authObj.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(authObj);

                log.info("JWT Valid, done set SecHoler");

            }

        }

        filterChain.doFilter(request, response);
    }
}

package com.awan.securityjwt.security.filter;

import com.awan.securityjwt.service.interfaces.JWTService;
import com.awan.securityjwt.service.interfaces.UserLocalService;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;
import java.util.Objects;

@Component
@RequiredArgsConstructor
@Slf4j
public class JWTFilterUserLocal extends OncePerRequestFilter {

    private final JWTService jwtService;
    private final UserLocalService userLocalService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        Claims claims = null;

        /* Parse JWT to Claims */
        String authorization = request.getHeader("Authorization");
        if (Objects.nonNull(authorization) && authorization.startsWith("Bearer")) {
            String jwtToken = authorization.substring(7);
            claims = jwtService.getClaims(jwtToken);
        }

        /* JWT Valid */
        if (claims != null) {

            Collection<? extends GrantedAuthority> authorities = jwtService.getAuthorityFromClaims(claims.get("Authorities"));

            /* SecContext belom diset */
            if (SecurityContextHolder.getContext().getAuthentication() == null) {

                PreAuthenticatedAuthenticationToken preAuth
                        = new PreAuthenticatedAuthenticationToken(claims.getSubject(), null, authorities);
                preAuth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(preAuth);
            }
        }

        filterChain.doFilter(request, response);
    }
}

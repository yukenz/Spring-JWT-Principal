package com.awan.securityjwt.security.filter;

import com.awan.securityjwt.service.interfaces.JWTService;
import com.awan.securityjwt.service.interfaces.UserLocalService;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
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
public class JWTFilter extends OncePerRequestFilter {

    private final JWTService jwtService;

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

            String b64Authorities = ((String) claims.get("Authorities"));
            Collection<? extends GrantedAuthority> authorities =
                    jwtService.getAuthorityFromClaims(b64Authorities);

            /* SecContext belom diset */
            if (SecurityContextHolder.getContext().getAuthentication() == null) {

                AbstractAuthenticationToken authentication
                        = new UsernamePasswordAuthenticationToken(claims.getSubject(), null, authorities);
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }

        filterChain.doFilter(request, response);
    }
}

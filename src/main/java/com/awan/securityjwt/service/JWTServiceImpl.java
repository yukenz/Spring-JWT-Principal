package com.awan.securityjwt.service;

import com.awan.securityjwt.service.interfaces.JWTService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class JWTServiceImpl implements JWTService {


    private final String jwtKey = Base64.getEncoder().encodeToString("awan123".getBytes());

    @Override
    public String generateJWT(UserDetails userDetails) {

        Map<String, Object> mapClaims = new HashMap<>();

        Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();

        mapClaims.put("Authorities", authorities);

        return Jwts.builder()
                .setClaims(mapClaims) //Claim must First
                .setSubject(userDetails.getUsername()) //Subject After Claims
                .setIssuedAt(new Date(System.currentTimeMillis()))
//                .setExpiration(new Date(System.currentTimeMillis() + Duration.ofSeconds(1).toMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + Duration.ofMinutes(30).toMillis()))
                .signWith(SignatureAlgorithm.HS256, jwtKey)
                .compact();
    }

    @Deprecated
    public Boolean validateJWTExpired(Claims claims) {
        // apakah username svama, dan token belom kedaluarsa
        return claims.getExpiration().after(new Date(System.currentTimeMillis())
        );
    }

    @Override
    public String getSubjectJWT(String token) {
        return Jwts.parser()
                .setSigningKey(jwtKey)
                .parseClaimsJws(token)
                .getBody().getSubject();
    }

    @Override
    public Claims getClaims(String token) {


        try {

            return Jwts.parser().setSigningKey(jwtKey)
                    .parseClaimsJws(token).getBody();

        } catch (RuntimeException e) {
            log.error(e.getMessage());
            return null;
        }
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorityFromClaims(Object claimAuthority) {
        try {
            if (claimAuthority instanceof Collection) {
                return ((Collection<Map>) claimAuthority).stream()
                        .map(colections -> {
                            Object o = ((Map<String, String>) colections).get("authority");
                            return new SimpleGrantedAuthority((String) o);
                        }).collect(Collectors.toList());
            }
            throw new IllegalArgumentException();
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }

}

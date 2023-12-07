package com.awan.securityjwt.service;

import com.awan.securityjwt.service.interfaces.JWTService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;

@Service
public class JWTServiceImpl implements JWTService {


    private final String jwtKey = Base64.getEncoder().encodeToString("awan123".getBytes());

    @Override
    public String generateJWT(UserDetails userDetails) {

        //Buat CLaims
        HashMap<String, Object> claims = new HashMap<>();

        //Jwts builder, set issued,expiration,signmethod
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + Duration.ofMinutes(30).toMillis()))
                .signWith(SignatureAlgorithm.HS256, jwtKey)
                .compact();

    }

    @Override
    public Boolean validateJWT(String token, UserDetails userDetails) {

        Claims claims = Jwts.parser()
                .setSigningKey(jwtKey)
                .parseClaimsJws(token)
                .getBody();

        String subject = claims.getSubject();

        // apakah username svama, dan token belom kedaluarsa
        return (
                userDetails.getUsername().equals(subject)
                        && claims.getExpiration().after(new Date(System.currentTimeMillis()))
        );
    }

    @Override
    public String getSubjectJWT(String token) {
        return Jwts.parser()
                .setSigningKey(jwtKey)
                .parseClaimsJws(token)
                .getBody().getSubject();
    }
}

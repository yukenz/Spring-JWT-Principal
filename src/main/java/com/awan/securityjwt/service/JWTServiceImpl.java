package com.awan.securityjwt.service;

import com.awan.securityjwt.service.interfaces.JWTService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.util.SerializationUtils;

import java.time.Duration;
import java.util.*;

@Service
@Slf4j
public class JWTServiceImpl implements JWTService {


    private final String jwtKey = Base64.getEncoder().encodeToString("awan123".getBytes());

    @Override
    public String generateJWT(UserDetails userDetails) {

        Map<String, Object> mapClaims = new HashMap<>();

        /* Serialize Object to Bytes */
        byte[] authoritiesSerialize = SerializationUtils
                .serialize(userDetails.getAuthorities());

        /* Encode Bytes to Base64 for safe Transport */
        String b64Authorities = Base64.getEncoder()
                .encodeToString(authoritiesSerialize);

        mapClaims.put("Authorities", b64Authorities);

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
    public Collection<? extends GrantedAuthority> getAuthorityFromClaims(String b64Authorities) {
        try {
            byte[] decode = Base64.getDecoder().decode(b64Authorities);
            Object deserialize = SerializationUtils.deserialize(decode);

            if (deserialize instanceof Collection) {
                return (Collection) deserialize;
            }

            throw new ClassCastException();
        } catch (Exception e) {
            log.error("Failed to Deserialize Authority from JWT : {}", e.getMessage());
            return Collections.emptyList();
        }
    }


    @Deprecated
    public String getSubjectJWT(String token) {
        return Jwts.parser()
                .setSigningKey(jwtKey)
                .parseClaimsJws(token)
                .getBody().getSubject();
    }

}

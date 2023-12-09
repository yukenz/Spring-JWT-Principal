package com.awan.securityjwt.service.interfaces;

import io.jsonwebtoken.Claims;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

public interface JWTService {

    String generateJWT(UserDetails userDetails);

    Boolean validateJWT(String token, UserDetails userDetails);

    String getSubjectJWT(String token);

    Claims getClaims(String token);

}

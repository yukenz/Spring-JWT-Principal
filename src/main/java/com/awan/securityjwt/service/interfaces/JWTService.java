package com.awan.securityjwt.service.interfaces;

import io.jsonwebtoken.Claims;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public interface JWTService {

    String generateJWT(UserDetails userDetails);

    Claims getClaims(String token);

    /**
     * Mengambil collections GrantedAuthority dari hasil Claims
     *
     * @param b64Authorities Hasil Claims object {@code Collection<? extends GrantedAuthority>}
     * @return Collection<String> | Empty if fail
     */
    Collection<? extends GrantedAuthority> getAuthorityFromClaims(String b64Authorities);

}

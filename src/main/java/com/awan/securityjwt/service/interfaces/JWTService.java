package com.awan.securityjwt.service.interfaces;

import io.jsonwebtoken.Claims;
import org.springframework.lang.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Map;

public interface JWTService {

    String generateJWT(UserDetails userDetails);

    String getSubjectJWT(String token);

    Claims getClaims(String token);

    /**
     * Mengambil collections GrantedAuthority dari hasil Claims
     *
     * @param claimAuthority Hasil Claims object {@code Collection<? extends GrantedAuthority>}
     * @return Collection<String> | Empty if fail
     */
    Collection<? extends GrantedAuthority> getAuthorityFromClaims(Object claimAuthority);

}

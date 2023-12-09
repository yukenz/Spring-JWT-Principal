package com.awan.securityjwt;

import com.awan.securityjwt.service.interfaces.JWTService;
import com.awan.securityjwt.service.interfaces.UserLocalService;
import io.jsonwebtoken.Claims;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.lang.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;

@SpringBootTest
public class JWTParseTest {

    @Autowired
    UserLocalService userLocalService;

    @Autowired
    JWTService jwtService;

    @Test
    void testparse() {

        UserDetails userDetails = userLocalService.loadUserByUsername("yukenz");
        String s = jwtService.generateJWT(userDetails);
        System.out.println(s);

        Claims claims = jwtService.getClaims(s);

        System.out.println(claims.getSubject());

        ArrayList<String> strings = new ArrayList<>();

        Object o = claims.get("lol123");
        System.out.println(getAuthorityFromClaims(o));

//        System.out.println(getAuthorityFromClaims(Collections.singleton(new Object())));


    }

    /**
     * Mengambil collections GrantedAuthority dari hasil Claims
     *
     * @param claimAuthority Hasil Claims object {@code Collection<? extends GrantedAuthority>}
     * @return Collection<String> | null
     */
    @Nullable
    Collection<? extends GrantedAuthority> getAuthorityFromClaims(Object claimAuthority) {
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

package com.awan.securityjwt;

import com.awan.securityjwt.service.interfaces.JWTService;
import com.awan.securityjwt.service.interfaces.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;

@SpringBootTest
public class JWTParseTest {

    @Autowired
    UserService userService;

    @Autowired
    JWTService jwtService;

    @Test
    void testparse() {

        UserDetails userDetails = userService.loadUserByUsername("yukenz");
        String s = jwtService.generateJWT(userDetails);
        System.out.println(s);
        String subjectJWT = jwtService.getSubjectJWT(s);
        System.out.println(subjectJWT);

    }
}

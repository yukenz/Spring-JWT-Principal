package com.awan.securityjwt;

import com.awan.securityjwt.service.interfaces.JWTService;
import com.awan.securityjwt.service.interfaces.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;

@SpringBootTest
public class ServiceTest {

    @Autowired
    JWTService jwtService;

    @Autowired
    UserService userService;

    @Test
    void notNull() {
        Assertions.assertNotNull(jwtService);
        Assertions.assertNotNull(userService);

        UserDetails userDetails = userService.loadUserByUsername("yukenz");
        Assertions.assertNotNull(userDetails);

    }
}

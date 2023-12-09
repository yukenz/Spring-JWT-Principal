package com.awan.securityjwt;

import com.awan.securityjwt.controller.AuthController;
import com.awan.securityjwt.model.request.AuthRequest;
import com.awan.securityjwt.model.response.AuthResponse;
import com.awan.securityjwt.model.response.ResponsePayload;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;

@SpringBootTest
public class AuthTest {

    @Autowired
    AuthController authController;

    @Autowired
    AuthenticationManager authenticationManager;

    @Test
    void testAuth() {
        AuthRequest request = AuthRequest.builder()
                .username("yukenz")
                .password("awan")
                .build();

        ResponsePayload<AuthResponse> auth = authController.auth(request);

        Assertions.assertNotNull(auth.getData().getToken());

    }

    @Test
    void authmanTest() {

        Authentication authenticate = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        "ypurniawans", "awan"
                )
        );

        UserDetails principal = (UserDetails) authenticate.getPrincipal();
        System.out.println(principal);
    }
}

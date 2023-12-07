package com.awan.securityjwt;

import com.awan.securityjwt.controller.AuthController;
import com.awan.securityjwt.model.request.AuthRequest;
import com.awan.securityjwt.model.response.AuthResponse;
import com.awan.securityjwt.model.response.ResponsePayload;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class AuthTest {

    @Autowired
    AuthController authController;

    @Test
    void testAuth() {
        AuthRequest request = AuthRequest.builder()
                .username("yukenz")
                .password("awan")
                .build();

        ResponsePayload<AuthResponse> auth = authController.auth(request) ;

        Assertions.assertNotNull(auth.getData().getToken());

    }
}

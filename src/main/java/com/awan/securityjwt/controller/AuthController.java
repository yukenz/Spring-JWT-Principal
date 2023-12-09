package com.awan.securityjwt.controller;

import com.awan.securityjwt.model.request.AuthRequest;
import com.awan.securityjwt.model.response.AuthResponse;
import com.awan.securityjwt.model.response.ResponsePayload;
import com.awan.securityjwt.service.interfaces.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;
import java.util.Date;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping(
            path = "/api/auth",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponsePayload<AuthResponse> auth(
            @RequestBody AuthRequest request
    ) {


        String token = authService.auth(request);

        AuthResponse response = AuthResponse.builder()
                .token(token)
                .validUntil(new Date(System.currentTimeMillis() + Duration.ofMinutes(30).toMillis()))
                .build();

        return ResponsePayload.<AuthResponse>builder()
                .message("Auth Success")
                .data(response)
                .build();

    }

}

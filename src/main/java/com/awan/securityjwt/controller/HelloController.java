package com.awan.securityjwt.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@Slf4j
public class HelloController {

    @GetMapping(
            path = "/hello"
    )
    public String hello(Authentication authentication) {

//        PreAuthenticatedAuthenticationToken principal = (PreAuthenticatedAuthenticationToken) authentication.getPrincipal();

//        log.info("{}", );

        return "Hello ";
    }

}

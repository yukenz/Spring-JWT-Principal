package com.awan.securityjwt.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping(
            path = "/hello"
    )
    public String hello(@AuthenticationPrincipal UserDetails principal) {

        System.out.println(principal);

        return "Hello ";
    }

}

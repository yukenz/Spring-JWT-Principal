package com.awan.securityjwt.controller.advice;

import com.awan.securityjwt.model.response.ResponsePayload;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class AuthAdvice {

    @ExceptionHandler({AuthenticationException.class})
    ResponseEntity<ResponsePayload<String>> authenticationException(AuthenticationException ex) {

        ResponsePayload<String> res = ResponsePayload.<String>builder().data(ex.getMessage()).status(00).message("Auth Fail").build();
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(res);

    }

}

package com.awan.securityjwt.service;

import com.awan.securityjwt.entity.User;
import com.awan.securityjwt.model.request.AuthRequest;
import com.awan.securityjwt.service.interfaces.AuthService;
import com.awan.securityjwt.service.interfaces.JWTService;
import com.awan.securityjwt.service.interfaces.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserService userService;
    private final JWTService jwtService;
    private final AuthenticationManager authenticationManager;

    @Override
    public String auth(AuthRequest request) {

        String username = request.getUsername();
        String password = request.getPassword();

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, password)
            );
        } catch (DisabledException e) {
            throw new RuntimeException("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new RuntimeException("INVALID_CREDENTIALS", e);
        }

        User user = userService.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("404 User"));

        return jwtService.generateJWT(
                userService.loadUserByUsername(user.getUsername()));

    }
}

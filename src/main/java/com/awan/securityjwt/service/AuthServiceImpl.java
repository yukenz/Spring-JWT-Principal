package com.awan.securityjwt.service;

import com.awan.securityjwt.model.request.AuthRequest;
import com.awan.securityjwt.service.interfaces.AuthService;
import com.awan.securityjwt.service.interfaces.JWTService;
import com.awan.securityjwt.service.interfaces.UserLocalService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserLocalService userLocalService;
    private final JWTService jwtService;
    private final AuthenticationManager authenticationManager;

    @Override
    public String auth(AuthRequest request) {

        String username = request.getUsername();
        String password = request.getPassword();

        try {
            Authentication authenticate = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, password)
            );

            Object principal = authenticate.getPrincipal();

            if (principal instanceof UserDetails) {
                return jwtService.generateJWT((UserDetails) principal);
            }

        } catch (DisabledException e) {
            throw new RuntimeException("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new RuntimeException("INVALID_CREDENTIALS", e);
        } catch (LockedException e) {
            throw new RuntimeException("USER_LOCKED", e);
        }

        throw new BadCredentialsException("Invalid Principal");

    }
}

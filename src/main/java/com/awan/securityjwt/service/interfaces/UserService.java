package com.awan.securityjwt.service.interfaces;

import com.awan.securityjwt.entity.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Optional;

public interface UserService extends UserDetailsService {

    Optional<User> findByUsername(String username);

}

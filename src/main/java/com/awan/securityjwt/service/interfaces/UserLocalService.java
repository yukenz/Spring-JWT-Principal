package com.awan.securityjwt.service.interfaces;

import com.awan.securityjwt.model.entity.UserLocal;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Optional;

public interface UserLocalService extends UserDetailsService {

    Optional<UserLocal> findByUsername(String username);

}

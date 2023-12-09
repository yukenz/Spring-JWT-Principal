package com.awan.securityjwt.service;

import com.awan.securityjwt.model.entity.UserLDAP;
import com.awan.securityjwt.repository.UserLDAPRepository;
import com.awan.securityjwt.service.interfaces.UserLDAPService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserLDAPServiceImpl implements UserLDAPService {

    private final UserLDAPRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UserLDAP userLDAP = repository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("USER NOT FOUND"));

        return User.withDefaultPasswordEncoder()
                .username(userLDAP.getUsername())
                .password(new String(userLDAP.getPassword()))
                .roles(String.valueOf(userLDAP.getGid()))
                .build();
    }
}

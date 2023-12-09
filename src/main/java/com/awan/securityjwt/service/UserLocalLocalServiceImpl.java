package com.awan.securityjwt.service;

import com.awan.securityjwt.model.entity.UserLocal;
import com.awan.securityjwt.service.interfaces.UserLocalService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserLocalLocalServiceImpl implements UserLocalService {

    private final List<UserLocal> userLocals = new ArrayList<>();

    @PostConstruct
    private void init() {

        UserLocal userLocal = UserLocal.builder()
                .username("yukenz")
                .password("awan")
                .fullName("Yuyun Purniawan")
                .age(Byte.valueOf("21"))
                .build();

        userLocals.add(userLocal);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<UserLocal> byUsername = findByUsername(username);

        if (!byUsername.isPresent()) {
            throw new UsernameNotFoundException("LocalUser tidak ditemukan");
        }

        UserLocal userLocal = byUsername.get();

        return org.springframework.security.core.userdetails.User.withDefaultPasswordEncoder()
                .username(userLocal.getUsername())
                .password(userLocal.getPassword())
                .roles("USER","EMPLOYEE")
                .build();


    }


    @Override
    public Optional<UserLocal> findByUsername(String username) {

        return userLocals.stream()
                .filter(userLocal -> userLocal.getUsername().equals(username))
                .findFirst();

    }
}

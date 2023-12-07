package com.awan.securityjwt.service;

import com.awan.securityjwt.entity.User;
import com.awan.securityjwt.service.interfaces.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final List<User> users = new ArrayList<>();

    @PostConstruct
    private void init() {

        User user = User.builder()
                .username("yukenz")
                .password("awan")
                .fullName("Yuyun Purniawan")
                .age(Byte.valueOf("21"))
                .build();

        users.add(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<User> byUsername = findByUsername(username);

        if (!byUsername.isPresent()) {
            throw new UsernameNotFoundException("User tidak ditemukan");
        }

        User user = byUsername.get();

        return org.springframework.security.core.userdetails.User.withDefaultPasswordEncoder()
                .username(user.getUsername())
                .password(user.getPassword())
                .roles("USER", "ADMIN")
                .build();


    }


    @Override
    public Optional<User> findByUsername(String username) {

        return users.stream()
                .filter(user -> user.getUsername().equals(username))
                .findFirst();

    }
}

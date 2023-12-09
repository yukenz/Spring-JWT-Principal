package com.awan.securityjwt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.ldap.repository.config.EnableLdapRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@SpringBootApplication
@EnableLdapRepositories
@EnableAspectJAutoProxy
@EnableWebSecurity
@EnableScheduling
public class SecurityjwtApplication {

    public static void main(String[] args) {
        SpringApplication.run(SecurityjwtApplication.class, args);
    }

}

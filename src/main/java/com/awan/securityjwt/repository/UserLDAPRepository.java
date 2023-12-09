package com.awan.securityjwt.repository;

import com.awan.securityjwt.model.entity.UserLDAP;
import org.springframework.data.ldap.repository.LdapRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserLDAPRepository extends LdapRepository<UserLDAP> {

    Optional<UserLDAP> findByUsername(String username);

}

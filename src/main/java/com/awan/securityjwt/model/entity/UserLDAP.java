package com.awan.securityjwt.model.entity;

import lombok.Data;
import org.springframework.ldap.odm.annotations.Attribute;
import org.springframework.ldap.odm.annotations.Entry;
import org.springframework.ldap.odm.annotations.Id;

import javax.naming.Name;
import java.io.Serializable;

@Entry(
        objectClasses = {"person", "inetOrgPerson", "top"}
)
@Data
public class UserLDAP implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private Name id;

    private @Attribute(name = "uid") String username;
    private @Attribute(name = "gidNumber") Integer gid;
    private @Attribute(name = "loginShell") String shell;
    private @Attribute(name = "uidNumber") Integer uid;
    private @Attribute(name = "cn") String commonName;
    private @Attribute(name = "givenName") String givenName;
    private @Attribute(name = "sn") String surName;
    private @Attribute(name = "homeDirectory") String homeDir;
    private @Attribute(name = "userPassword") byte[] password;

}

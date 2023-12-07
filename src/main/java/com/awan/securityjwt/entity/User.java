package com.awan.securityjwt.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User implements Cloneable {

    private String username;
    private String password;
    private String fullName;
    private Byte age;

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}

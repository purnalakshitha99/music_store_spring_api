package com.musicstore.musicstore.dto.request;


import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;

import javax.management.relation.Role;

@Data
public class RegisterRq {

    private String firstName;
    private String lastName;
    private String email;
    private String password;
    @Enumerated(EnumType.STRING)
    private Role role;
}

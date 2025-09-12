package com.musicstore.musicstore.dto.request;

import com.musicstore.musicstore.model.ROLES;
import lombok.Data;

@Data
public class RegisterRq {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private ROLES role;

}
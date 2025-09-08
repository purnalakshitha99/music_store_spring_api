package com.musicstore.musicstore.dto.response;

import lombok.Data;

@Data
public class RegisterResponse {

    private String firstName;
    private String lastName;
    private String email;
    private String password;

    private String message;
}

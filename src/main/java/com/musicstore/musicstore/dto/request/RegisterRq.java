package com.musicstore.musicstore.dto.request;


import lombok.Data;

@Data
public class RegisterRq {

    private String firstName;
    private String lastName;
    private String email;
    private String password;
}

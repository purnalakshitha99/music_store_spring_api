package com.musicstore.musicstore.dto.response;

import lombok.Data;

@Data
public class LoginResponse {
    private String message;
    private String accessToken;
    private String tokenType = "Bearer";
    private UserResponseDto user;
}
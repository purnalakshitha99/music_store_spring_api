package com.musicstore.musicstore.dto.response;

import com.musicstore.musicstore.model.ROLES;
import lombok.Data;


@Data
public class UserResponseDto {

    private Long id;
    private String email;
    private String firstName;
    private String lastName;
    private ROLES role;
}

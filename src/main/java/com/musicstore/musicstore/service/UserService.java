package com.musicstore.musicstore.service;

import com.musicstore.musicstore.dto.response.UserResponseDto;
import com.musicstore.musicstore.exception.UserNotFoundException;
import com.musicstore.musicstore.model.ROLES;
import com.musicstore.musicstore.model.User;

import java.util.List;

public interface UserService  {

    List<UserResponseDto> getUsers()throws UserNotFoundException;
}

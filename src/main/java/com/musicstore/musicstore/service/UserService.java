package com.musicstore.musicstore.service;

import com.musicstore.musicstore.dto.response.UserResponseDto;
import com.musicstore.musicstore.exception.UserNotFoundException;

import java.util.List;

public interface UserService  {

    List<UserResponseDto> getUsers()throws UserNotFoundException;
    List<UserResponseDto> getArtists() throws UserNotFoundException;
    List<UserResponseDto>getAdvertisementManagers() throws UserNotFoundException;

}

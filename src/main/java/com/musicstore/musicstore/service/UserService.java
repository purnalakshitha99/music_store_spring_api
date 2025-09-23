package com.musicstore.musicstore.service;

import com.musicstore.musicstore.dto.response.DeleteResponse;
import com.musicstore.musicstore.dto.response.UserResponseDto;
import com.musicstore.musicstore.exception.UserNotFoundException;

import java.util.List;
import java.util.Optional;

public interface UserService  {

    List<UserResponseDto> getUsers()throws UserNotFoundException;
    List<UserResponseDto> getArtists() throws UserNotFoundException;
    List<UserResponseDto>getAdvertisementManagers() throws UserNotFoundException;
    Optional<DeleteResponse> deleteUser(Long id) throws UserNotFoundException;
    UserResponseDto getUserById(Long id) throws UserNotFoundException;


}

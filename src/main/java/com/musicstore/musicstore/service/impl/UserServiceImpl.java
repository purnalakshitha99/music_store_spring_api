package com.musicstore.musicstore.service.impl;

import com.musicstore.musicstore.dto.response.DeleteResponse;
import com.musicstore.musicstore.dto.response.UserResponseDto;
import com.musicstore.musicstore.exception.UserNotFoundException;
import com.musicstore.musicstore.model.User;
import com.musicstore.musicstore.model.ROLES;
import com.musicstore.musicstore.repository.UserRepository;
import com.musicstore.musicstore.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;


    @Override
    public List<UserResponseDto> getUsers() throws UserNotFoundException {

        List<User> userList = userRepository.findAll();

        if (userList.isEmpty()) {
            throw new UserNotFoundException("Users are not found");
        }

        List<UserResponseDto> userResponseDtoList = new ArrayList<>();

        for (User user : userList) {
            UserResponseDto userResponseDto = new UserResponseDto();

            userResponseDto.setId(user.getId());
            userResponseDto.setEmail(user.getEmail());
            userResponseDto.setFirstName(user.getFirstName());
            userResponseDto.setLastName(user.getLastName());
            userResponseDto.setProfilePictureUrl(user.getProfilePictureUrl());

            userResponseDtoList.add(userResponseDto);
        }

        return userResponseDtoList;


    }

    @Override
    public List<UserResponseDto> getArtists() throws UserNotFoundException {
        List<User> artists = userRepository.findByRole(ROLES.ROLE_ARTIST);
        if (artists.isEmpty()) {
            throw new UserNotFoundException("Artists are not found");
        }
        List<UserResponseDto> result = new ArrayList<>();
        for (User user : artists) {
            UserResponseDto dto = new UserResponseDto();
            dto.setId(user.getId());
            dto.setEmail(user.getEmail());
            dto.setFirstName(user.getFirstName());
            dto.setLastName(user.getLastName());
            dto.setProfilePictureUrl(user.getProfilePictureUrl());
            result.add(dto);
        }
        return result;
    }


    public List<UserResponseDto> getAdvertisementManagers() throws UserNotFoundException {
        List<User> artists = userRepository.findByRole(ROLES.ROLE_ADVERTISEMENT_MANAGER);
        if (artists.isEmpty()) {
            throw new UserNotFoundException("advertisement managers are not found");
        }
        List<UserResponseDto> result = new ArrayList<>();
        for (User user : artists) {
            UserResponseDto dto = new UserResponseDto();
            dto.setId(user.getId());
            dto.setEmail(user.getEmail());
            dto.setFirstName(user.getFirstName());
            dto.setLastName(user.getLastName());
            dto.setProfilePictureUrl(user.getProfilePictureUrl());
            result.add(dto);
        }
        return result;
    }

    public Optional<DeleteResponse> deleteUser(Long id) throws UserNotFoundException {

        User user = userRepository.findById(id).orElseThrow(
                () -> new UserNotFoundException("User not found")
        );
        userRepository.delete(user);

        DeleteResponse deleteResponse = new DeleteResponse();

        deleteResponse.setResponseId(user.getId());
        deleteResponse.setMessage("User deleted successfully with id " + user.getId());

        return Optional.of(deleteResponse);
    }


    @Override
    public UserResponseDto getUserById(Long id) throws UserNotFoundException {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found with id " + id));

        UserResponseDto dto = new UserResponseDto();
        dto.setId(user.getId());
        dto.setEmail(user.getEmail());
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());
        dto.setProfilePictureUrl(user.getProfilePictureUrl());
        return dto;
    }



}

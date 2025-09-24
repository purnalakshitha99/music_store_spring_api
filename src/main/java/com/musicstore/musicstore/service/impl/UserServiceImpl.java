package com.musicstore.musicstore.service.impl;

import com.musicstore.musicstore.dto.request.RegisterRq;
import com.musicstore.musicstore.dto.response.DeleteResponse;
import com.musicstore.musicstore.dto.response.UserResponseDto;
import com.musicstore.musicstore.exception.UserNotFoundException;
import com.musicstore.musicstore.model.User;
import com.musicstore.musicstore.model.ROLES;
import com.musicstore.musicstore.repository.UserRepository;
import com.musicstore.musicstore.service.CloudinaryService;
import com.musicstore.musicstore.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final CloudinaryService cloudinaryService;
    private final PasswordEncoder passwordEncoder;


    @Override
    public List<UserResponseDto> getUsers() throws UserNotFoundException {

        List<User> userList = userRepository.findAll();

        if (userList.isEmpty()) {
            throw new UserNotFoundException("Users are not found");
        }

        return getUserResponseDtos(userList);


    }

    private List<UserResponseDto> getUserResponseDtos(List<User> userList) {
        List<UserResponseDto> userResponseDtoList = new ArrayList<>();

        for (User user : userList) {
            UserResponseDto userResponseDto = new UserResponseDto();

            userResponseDto.setId(user.getId());
            userResponseDto.setEmail(user.getEmail());
            userResponseDto.setFirstName(user.getFirstName());
            userResponseDto.setLastName(user.getLastName());
            userResponseDto.setProfilePictureUrl(user.getProfilePictureUrl());
            userResponseDto.setRole(user.getRole());

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
        return getUserResponseDtos(artists);
    }


    public List<UserResponseDto> getAdvertisementManagers() throws UserNotFoundException {
        List<User> artists = userRepository.findByRole(ROLES.ROLE_ADVERTISEMENT_MANAGER);
        if (artists.isEmpty()) {
            throw new UserNotFoundException("advertisement managers are not found");
        }
        return getUserResponseDtos(artists);
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

        return getUserResponseDto(user);
    }


    @Override
    public UserResponseDto updateUser(Long id, RegisterRq registerRq, MultipartFile file) throws UserNotFoundException {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found with id " + id));

        // 1. Update basic fields if provided
        if (registerRq.getFirstName() != null) {
            user.setFirstName(registerRq.getFirstName());
        }
        if (registerRq.getLastName() != null) {
            user.setLastName(registerRq.getLastName());
        }
        if (registerRq.getEmail() != null && !registerRq.getEmail().equals(user.getEmail())) {
            if (userRepository.existsByEmail(registerRq.getEmail())) {
                throw new RuntimeException("Error: Email is already in use!");
            }
            user.setEmail(registerRq.getEmail());
        }

        // 2. Update password (if provided)
        if (registerRq.getPassword() != null && !registerRq.getPassword().isBlank()) {
            user.setPassword(passwordEncoder.encode(registerRq.getPassword()));
        }

        // 3. Update role safely (RegisterRq.role is an enum)
        if (registerRq.getRole() != null) {
            user.setRole(registerRq.getRole());
        }

        // 4. Handle profile picture upload
        if (file != null && !file.isEmpty()) {
            String imageUrl = cloudinaryService.uploadFile(file, "profile_pictures");
            user.setProfilePictureUrl(imageUrl);
        }

        // 5. Save updated user
        User updatedUser = userRepository.save(user);

        // 6. Prepare response
        return getUserResponseDto(updatedUser);
    }

    private UserResponseDto getUserResponseDto(User updatedUser) {
        UserResponseDto dto = new UserResponseDto();
        dto.setId(updatedUser.getId());
        dto.setEmail(updatedUser.getEmail());
        dto.setFirstName(updatedUser.getFirstName());
        dto.setLastName(updatedUser.getLastName());
        dto.setProfilePictureUrl(updatedUser.getProfilePictureUrl());
        dto.setRole(updatedUser.getRole());
        return dto;
    }
}




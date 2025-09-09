package com.musicstore.musicstore.service.impl;

import com.musicstore.musicstore.dto.response.UserResponseDto;
import com.musicstore.musicstore.exception.UserNotFoundException;
import com.musicstore.musicstore.model.User;
import com.musicstore.musicstore.repository.UserRepository;
import com.musicstore.musicstore.service.UserService;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

            userResponseDtoList.add(userResponseDto);
        }

        return userResponseDtoList;


    }


}

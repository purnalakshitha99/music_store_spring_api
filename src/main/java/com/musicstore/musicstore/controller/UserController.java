package com.musicstore.musicstore.controller;

import com.musicstore.musicstore.dto.response.UserResponseDto;
import com.musicstore.musicstore.exception.UserNotFoundException;
import com.musicstore.musicstore.model.User;
import com.musicstore.musicstore.repository.UserRepository;
import com.musicstore.musicstore.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/admin")
public class UserController {

    private UserService userService;

//    public ResponseEntity<> List <UserResponseDto> getAllUsers()throws UserNotFoundException {
//
//        List<UserResponseDto> userResponseDtoList = userService.getUsers();
//
//        re
//
//    }

    @GetMapping("/users")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<UserResponseDto>> getUsers()throws UserNotFoundException {

        List<UserResponseDto> userResponseDtoList = userService.getUsers();

        return new ResponseEntity<>(userResponseDtoList, HttpStatus.OK);
    }


}

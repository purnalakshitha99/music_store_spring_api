package com.musicstore.musicstore.controller;

import com.musicstore.musicstore.dto.response.DeleteResponse;
import com.musicstore.musicstore.dto.response.UserResponseDto;
import com.musicstore.musicstore.exception.UserNotFoundException;
import com.musicstore.musicstore.model.User;
import com.musicstore.musicstore.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
@RequestMapping("/api/admin")
public class UserController {

    private UserService userService;


    @GetMapping("/users")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<UserResponseDto>> getUsers()throws UserNotFoundException {

        List<UserResponseDto> userResponseDtoList = userService.getUsers();

        return new ResponseEntity<>(userResponseDtoList, HttpStatus.OK);
    }


    @GetMapping("/users/advertisement_managers")
//    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PreAuthorize("hasAnyRole('ADMIN', 'ADVERTISEMENT_MANAGER')")
    public ResponseEntity<List<UserResponseDto>> getAdvertisementManagers() throws UserNotFoundException {

        List<UserResponseDto> userResponseDtoList = userService.getAdvertisementManagers();

        return new ResponseEntity<>(userResponseDtoList, HttpStatus.OK);
    }


    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/users/{user_id}")
    public ResponseEntity<DeleteResponse> deleteUser(@PathVariable("user_id")Long userId) throws UserNotFoundException {

        Optional<DeleteResponse> deleteResponse = userService.deleteUser(userId);

        return new ResponseEntity<>(deleteResponse.orElseGet(DeleteResponse::new), HttpStatus.OK);

    }

    @GetMapping("/users/{user_id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<UserResponseDto> getUserById(@PathVariable("user_id") Long userId) throws UserNotFoundException {
        UserResponseDto userResponseDto = userService.getUserById(userId);
        return new ResponseEntity<>(userResponseDto, HttpStatus.OK);
    }






}

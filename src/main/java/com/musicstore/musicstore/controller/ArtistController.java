package com.musicstore.musicstore.controller;


import com.musicstore.musicstore.dto.response.UserResponseDto;
import com.musicstore.musicstore.exception.UserNotFoundException;
import com.musicstore.musicstore.service.AuthService;
import com.musicstore.musicstore.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/artists")
@AllArgsConstructor
// This opens up all endpoints in this controller to requests from localhost:3000
@CrossOrigin(origins = "http://localhost:5173")
public class ArtistController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<UserResponseDto>> getArtists() throws UserNotFoundException {
        List<UserResponseDto> artists = userService.getArtists();
        return new ResponseEntity<>(artists, HttpStatus.OK);
    }
}

package com.musicstore.musicstore.controller;

import com.musicstore.musicstore.dto.request.SongRq;
import com.musicstore.musicstore.dto.response.SongResponse;
import com.musicstore.musicstore.service.SongService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/songs")
@AllArgsConstructor
public class SongController {

    private final SongService songService;

    @PostMapping("/upload")
//    @PreAuthorize("hasRole('ROLE_ARTIST')") // The security rule that locks this endpoint
    public ResponseEntity<SongResponse> uploadSong(
            @RequestPart("songRq") SongRq songRq,
            @RequestPart("coverArtFile") MultipartFile coverArtFile,
            Authentication authentication // Spring injects this to identify the current user
    ) {
        // Securely get the artist's email from their JWT token
        String artistEmail = authentication.getName();

        SongResponse createdSong = songService.createSong(songRq, coverArtFile, artistEmail);
        return new ResponseEntity<>(createdSong, HttpStatus.CREATED);
    }
}
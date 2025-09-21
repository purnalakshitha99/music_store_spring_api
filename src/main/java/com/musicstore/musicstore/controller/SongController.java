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

import java.nio.file.AccessDeniedException;
import java.util.List;

@RestController
@RequestMapping("/api/songs")
@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
@AllArgsConstructor
public class SongController {

    private final SongService songService;

    @PostMapping("/upload")
    @PreAuthorize("hasRole('ARTIST')") // The security rule that locks this endpoint
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


    // GET endpoint to fetch all songs
    @GetMapping
    public ResponseEntity<List<SongResponse>> getAllSongs() {
        List<SongResponse> songs = songService.getAllSongs();
        return ResponseEntity.ok(songs);
    }

    // GET endpoint to fetch a single song by its ID
    @GetMapping("/{id}")
    public ResponseEntity<SongResponse> getSongById(@PathVariable Long id) {
        SongResponse song = songService.getSongById(id);
        return ResponseEntity.ok(song);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ARTIST') or hasRole('ADMIN')")
    public ResponseEntity<SongResponse> updateSong(
            @PathVariable Long id,
            @RequestPart("songRq") SongRq songRq,
            // Make the file optional for updates
            @RequestPart(value = "coverArtFile", required = false) MultipartFile coverArtFile,
            Authentication authentication
    ) throws AccessDeniedException {
        String currentUserEmail = authentication.getName();
        SongResponse updatedSong = songService.updateSong(id, songRq, coverArtFile, currentUserEmail);
        return ResponseEntity.ok(updatedSong);
    }

    // DELETE endpoint to remove a song
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ARTIST') or hasRole('ADMIN')")
    public ResponseEntity<String> deleteSong(@PathVariable Long id, Authentication authentication) throws AccessDeniedException {
        String currentUserEmail = authentication.getName();
        songService.deleteSong(id, currentUserEmail);
        return ResponseEntity.ok("Song with id " + id + " was deleted successfully.");
    }
}
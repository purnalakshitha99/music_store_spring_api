package com.musicstore.musicstore.service.impl;

import com.musicstore.musicstore.dto.request.SongRq;
import com.musicstore.musicstore.dto.response.SongResponse;
import com.musicstore.musicstore.model.ROLES;
import com.musicstore.musicstore.model.Song;
import com.musicstore.musicstore.model.User;
import com.musicstore.musicstore.repository.SongRepository;
import com.musicstore.musicstore.repository.UserRepository;
import com.musicstore.musicstore.service.CloudinaryService; // You should already have this
import com.musicstore.musicstore.service.SongService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.AccessDeniedException;
import java.util.List;

@Service
@AllArgsConstructor
public class SongServiceImpl implements SongService {

    private final SongRepository songRepository;
    private final UserRepository userRepository;
    private final CloudinaryService cloudinaryService;

    @Override
    @Transactional // Ensures the entire operation succeeds or fails together
    public SongResponse createSong(SongRq songRq, MultipartFile coverArtFile, String artistEmail) {
        // 1. Find the artist (the currently logged-in user) by their email
        User artist = userRepository.findByEmail(artistEmail)
                .orElseThrow(() -> new UsernameNotFoundException("Artist not found with email: " + artistEmail));

        // 2. Upload the cover art image to Cloudinary
        String coverArtUrl = cloudinaryService.uploadFile(coverArtFile, "cover_arts");

        // 3. Create a new Song entity and populate its fields
        Song newSong = new Song();
        newSong.setTitle(songRq.getTitle());
        newSong.setGenre(songRq.getGenre());
        newSong.setReleaseDate(songRq.getReleaseDate());
        newSong.setCoverArtUrl(coverArtUrl);
        newSong.setArtist(artist); // Link the song to the artist

        // 4. Save the new song entity to the database
        Song savedSong = songRepository.save(newSong);

        // 5. Map the saved entity to our response DTO and return it
        return mapToSongResponse(savedSong);
    }

    // Helper method to keep the mapping logic clean
    private SongResponse mapToSongResponse(Song song) {
        SongResponse responseDto = new SongResponse();
        responseDto.setId(song.getId());
        responseDto.setTitle(song.getTitle());
        responseDto.setGenre(song.getGenre());
        responseDto.setReleaseDate(song.getReleaseDate());
        responseDto.setCoverArtUrl(song.getCoverArtUrl());
        responseDto.setArtistName(song.getArtist().getFirstName() + " " + song.getArtist().getLastName());
        return responseDto;
    }

    @Override
    public SongResponse getSongById(Long id) {
        Song song = songRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Song not found with id: " + id)); // In a real app, use a custom ResourceNotFoundException
        return mapToSongResponse(song);
    }

    @Override
    public List<SongResponse> getAllSongs() {
        List<Song> songs = songRepository.findAll();
        // Use a stream to map each Song entity to a SongResponseDto
        return songs.stream()
                .map(this::mapToSongResponse)
                .toList();
    }


    @Override
    @Transactional
    public SongResponse updateSong(Long id, SongRq songRq, MultipartFile coverArtFile, String currentUserEmail) throws AccessDeniedException {
        // 1. Find the existing song
        Song existingSong = songRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Song not found with id: " + id));

        // 2. Find the current user
        User currentUser = userRepository.findByEmail(currentUserEmail)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        // 3. Authorization Check: Is the current user the owner OR an admin?
        if (!existingSong.getArtist().getId().equals(currentUser.getId()) && !currentUser.getRole().equals(ROLES.ROLE_ADMIN)) {
            throw new AccessDeniedException("You are not authorized to update this song.");
        }

        // 4. Update the song's details from the DTO
        existingSong.setTitle(songRq.getTitle());
        existingSong.setGenre(songRq.getGenre());
        existingSong.setReleaseDate(songRq.getReleaseDate());

        // 5. If a new cover art file is provided, upload it and update the URL
        if (coverArtFile != null && !coverArtFile.isEmpty()) {
            String newCoverArtUrl = cloudinaryService.uploadFile(coverArtFile, "cover_arts");
            existingSong.setCoverArtUrl(newCoverArtUrl);
            // Optional: You could add logic here to delete the old image from Cloudinary
        }

        // 6. Save the updated song
        Song updatedSong = songRepository.save(existingSong);
        return mapToSongResponse(updatedSong);
    }

    @Override
    @Transactional
    public void deleteSong(Long id, String currentUserEmail) throws AccessDeniedException {
        // 1. Find the existing song
        Song songToDelete = songRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Song not found with id: " + id));

        // 2. Find the current user
        User currentUser = userRepository.findByEmail(currentUserEmail)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        // 3. Authorization Check: Is the current user the owner OR an admin?
        if (!songToDelete.getArtist().getId().equals(currentUser.getId()) && !currentUser.getRole().equals(ROLES.ROLE_ADMIN)) {
            throw new AccessDeniedException("You are not authorized to delete this song.");
        }

        // 4. Delete the song from the database
        songRepository.delete(songToDelete);
        // Optional: You should add logic here to delete the cover art from Cloudinary
    }
}
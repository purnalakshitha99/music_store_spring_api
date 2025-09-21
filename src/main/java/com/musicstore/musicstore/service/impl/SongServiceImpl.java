package com.musicstore.musicstore.service.impl;

import com.musicstore.musicstore.dto.request.SongRq;
import com.musicstore.musicstore.dto.response.SongResponse;
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
}
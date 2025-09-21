package com.musicstore.musicstore.service;

import com.musicstore.musicstore.dto.request.SongRq;
import com.musicstore.musicstore.dto.response.SongResponse;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.AccessDeniedException;
import java.util.List;

public interface SongService {
    SongResponse createSong(SongRq songRq, MultipartFile coverArtFile, String artistEmail);
    SongResponse getSongById(Long id);
    List<SongResponse> getAllSongs();

    SongResponse updateSong(Long id, SongRq songRq, MultipartFile coverArtFile, String currentUserEmail) throws AccessDeniedException;
    void deleteSong(Long id, String currentUserEmail) throws AccessDeniedException;
}
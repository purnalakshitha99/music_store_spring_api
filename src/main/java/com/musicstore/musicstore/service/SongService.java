package com.musicstore.musicstore.service;

import com.musicstore.musicstore.dto.request.SongRq;
import com.musicstore.musicstore.dto.response.SongResponse;
import org.springframework.web.multipart.MultipartFile;

public interface SongService {
    SongResponse createSong(SongRq songRq, MultipartFile coverArtFile, String artistEmail);
}
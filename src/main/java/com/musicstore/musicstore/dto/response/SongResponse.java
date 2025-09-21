package com.musicstore.musicstore.dto.response;

import lombok.Data;
import java.time.LocalDate;

@Data
public class SongResponse{
    private Long id;
    private String title;
    private String genre;
    private LocalDate releaseDate;
    private String coverArtUrl;
    private String artistName; // Useful for the frontend to display the artist's name
}
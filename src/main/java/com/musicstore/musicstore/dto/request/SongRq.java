package com.musicstore.musicstore.dto.request;

import lombok.Data;
import java.time.LocalDate;

@Data
public class SongRq {
    private String title;
    private String genre;
    private LocalDate releaseDate;
}
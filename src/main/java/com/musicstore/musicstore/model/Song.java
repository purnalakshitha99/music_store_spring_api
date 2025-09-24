package com.musicstore.musicstore.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "songs")
public class Song {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(name = "genre") // 'type' is a reserved keyword in SQL, 'genre' is better
    private String genre;

    private LocalDate releaseDate;

//    @Column(length = 1024) // Allow for long URLs from Cloudinary
//    private String audioUrl; // To store the URL of the uploaded music file

    @Column(length = 1024)
    private String coverArtUrl; // To store the URL of the cover image

    // This creates the relationship: a Song is created by one User (the artist)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "artist_id", nullable = false)
    private User artist;
}
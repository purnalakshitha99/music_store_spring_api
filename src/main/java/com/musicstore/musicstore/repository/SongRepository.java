package com.musicstore.musicstore.repository;

import com.musicstore.musicstore.model.Song;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SongRepository extends JpaRepository <Song,Long> {
}

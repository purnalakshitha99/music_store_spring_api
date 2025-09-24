package com.musicstore.musicstore.repository;

import com.musicstore.musicstore.model.Advertisement;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;


public interface AdvertisementRepository extends JpaRepository<Advertisement, Long> {
}

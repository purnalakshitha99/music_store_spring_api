package com.musicstore.musicstore.controller;


import com.musicstore.musicstore.dto.request.AdvertisementRq;
import com.musicstore.musicstore.dto.response.AdvertisementResponse;
import com.musicstore.musicstore.exception.AdvertisementNotFoundException;
import com.musicstore.musicstore.exception.UserNotFoundException;
import com.musicstore.musicstore.model.Advertisement;
import com.musicstore.musicstore.repository.UserRepository;
import com.musicstore.musicstore.service.AdvertisementService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@AllArgsConstructor
@RestController
public class AdvertisementController {

    private final AdvertisementService advertisementService;
    @PreAuthorize("hasRole('ADVERTISEMENT_MANAGER') or hasRole('ADMIN')")
    @PostMapping("/advertisement_manager/{advertisement_manager_id}")
    public ResponseEntity<AdvertisementResponse> createAdvertisement(AdvertisementRq advertisementRq, @RequestParam("advertisement_manager_id")Long userId, @RequestParam(value = "file",required = false) MultipartFile file, @PathVariable String advertisement_manager_id)throws AdvertisementNotFoundException, UserNotFoundException {

        AdvertisementResponse advertisementResponse = advertisementService.createAdvertisement(advertisementRq,userId,file);

        return ResponseEntity.ok(advertisementResponse);


    }


}

package com.musicstore.musicstore.controller;


import com.musicstore.musicstore.dto.request.AdvertisementRq;
import com.musicstore.musicstore.dto.response.AdvertisementResponse;
import com.musicstore.musicstore.exception.AdvertisementNotFoundException;
import com.musicstore.musicstore.exception.UserNotFoundException;
import com.musicstore.musicstore.model.Advertisement;
import com.musicstore.musicstore.repository.UserRepository;
import com.musicstore.musicstore.service.AdvertisementService;
import jakarta.annotation.security.RolesAllowed;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@AllArgsConstructor
@RestController
@RequestMapping("/api/advertisement")
public class AdvertisementController {

    private final AdvertisementService advertisementService;
// //    @PreAuthorize("hasRole('ADVERTISEMENT_MANAGER') or hasRole('ADMIN')")
// @PreAuthorize("hasRole('ADMIN')")
//     @PostMapping("/advertisement_manager/{user_id}")
//     public ResponseEntity<AdvertisementResponse> createAdvertisement(AdvertisementRq advertisementRq, @RequestParam(value = "file",required = false) MultipartFile file, @PathVariable("user_id") Long userId)throws AdvertisementNotFoundException, UserNotFoundException {

//         AdvertisementResponse advertisementResponse = advertisementService.createAdvertisement(advertisementRq,userId,file);

//         return ResponseEntity.ok(advertisementResponse);


//     }

    @PostMapping("/add/{user_id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void createAdvertisement(
            @ModelAttribute AdvertisementRq advertisementRq,
            @RequestParam(value = "file", required = false) MultipartFile file,
            @PathVariable("user_id") Long userId
    ) {
        System.out.println("AdvertisementRq: " + advertisementRq);
        System.out.println("File: " + (file != null ? file.getOriginalFilename() : "No file uploaded"));
        System.out.println("User ID: " + userId);
    }



}

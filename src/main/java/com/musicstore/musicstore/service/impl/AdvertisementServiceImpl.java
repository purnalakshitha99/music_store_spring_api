package com.musicstore.musicstore.service.impl;

import com.musicstore.musicstore.dto.request.AdvertisementRq;
import com.musicstore.musicstore.dto.response.AdvertisementResponse;
import com.musicstore.musicstore.exception.AdvertisementNotFoundException;
import com.musicstore.musicstore.model.Advertisement;
import com.musicstore.musicstore.model.User;
import com.musicstore.musicstore.repository.AdvertisementRepository;
import com.musicstore.musicstore.repository.UserRepository;
import com.musicstore.musicstore.service.AdvertisementService;
import com.musicstore.musicstore.service.CloudinaryService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@AllArgsConstructor
public class AdvertisementServiceImpl implements AdvertisementService {

    private final AdvertisementRepository advertisementRepository;
    private final UserRepository userRepository;
    private final CloudinaryService cloudinaryService;

    public AdvertisementResponse createAdvertisement( AdvertisementRq advertisementRq,Long userId, MultipartFile file) throws AdvertisementNotFoundException {

        User user = userRepository.findById(userId).orElseThrow(
                () -> new AdvertisementNotFoundException("User not found")
        );
        
        Advertisement advertisement = new Advertisement();
        
        advertisement.setStartDate(advertisementRq.getStartDate());
        advertisement.setEndDate(advertisementRq.getEndDate());
        advertisement.setBudget(advertisementRq.getBudget());
        advertisement.setTitle(advertisementRq.getTitle());
        advertisement.setDescription(advertisementRq.getDescription());

        String imageUrl = null; // Default to null if no file is provided
        if (file != null && !file.isEmpty()) {
            // If a file is present, upload it to Cloudinary in a "profile_pictures" folder
            imageUrl = cloudinaryService.uploadFile(file, "advertisement_pictures");
        }
        
        advertisement.setAdvertisementUrl(imageUrl);
        
        advertisementRepository.save(advertisement);
        
        AdvertisementResponse advertisementResponse = new AdvertisementResponse();
        
        advertisementResponse.setAdvertisementUrl(imageUrl);
        advertisementResponse.setTitle(advertisement.getTitle());
        advertisementResponse.setDescription(advertisement.getDescription());
        advertisementResponse.setStartDate(advertisement.getStartDate());
        advertisementResponse.setEndDate(advertisement.getEndDate());
        advertisementResponse.setBudget(advertisement.getBudget());
        advertisementResponse.setId(advertisement.getId());
        
        return advertisementResponse;
        


    }
}

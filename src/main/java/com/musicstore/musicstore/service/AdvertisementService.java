package com.musicstore.musicstore.service;


import com.musicstore.musicstore.dto.request.AdvertisementRq;
import com.musicstore.musicstore.dto.response.AdvertisementResponse;
import com.musicstore.musicstore.exception.AdvertisementNotFoundException;
import com.musicstore.musicstore.exception.UserNotFoundException;
import com.musicstore.musicstore.exception.UserNotPermitionException;
import org.springframework.web.multipart.MultipartFile;

public interface AdvertisementService  {

    AdvertisementResponse createAdvertisement(AdvertisementRq advertisementRq, Long userId, MultipartFile file) throws AdvertisementNotFoundException, UserNotFoundException, UserNotPermitionException;
}

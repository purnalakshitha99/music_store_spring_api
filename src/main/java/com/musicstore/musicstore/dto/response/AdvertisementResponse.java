package com.musicstore.musicstore.dto.response;

import lombok.Data;

@Data
public class AdvertisementResponse {

    private Long id;
    private String title;
    private String description;
    private String advertisementUrl;
}

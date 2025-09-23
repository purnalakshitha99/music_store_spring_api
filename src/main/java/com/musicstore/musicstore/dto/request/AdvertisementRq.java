package com.musicstore.musicstore.dto.request;

import lombok.Data;

import java.util.Date;

@Data
public class AdvertisementRq {


    private String title;
    private String description;
    private String advertisementUrl;
    private Date startDate;

}

package com.musicstore.musicstore.model;


import lombok.Data;

@Data
public class Advertisement {

    private Long id;
    private String title;
    private String description;
    private String advertisementUrl;
}

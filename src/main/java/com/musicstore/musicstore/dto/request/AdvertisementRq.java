package com.musicstore.musicstore.dto.request;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;


@Data
public class AdvertisementRq {


    private String title;
    private String description;
    private LocalDate startDate;
    private LocalDate endDate;
    private BigDecimal budget;

}

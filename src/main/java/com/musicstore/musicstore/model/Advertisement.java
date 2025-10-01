package com.musicstore.musicstore.model;


import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "advertisement")
public class Advertisement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String description;
    private String advertisementUrl;
    private LocalDate startDate;
    private LocalDate endDate;
    private BigDecimal budget;

    @ManyToOne
    private User user;


}

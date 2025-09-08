package com.musicstore.musicstore.model;

import jakarta.persistence.*;
import lombok.Data;

import javax.management.relation.Role;

@Data
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;

    @Enumerated(EnumType.STRING)
    private ROLES role;


    public void setRole(Role role) {
    }
}

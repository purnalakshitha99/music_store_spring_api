package com.musicstore.musicstore.model;

//public enum ROLES {
//    ROLE_USER,
//    ROLE_ADMIN,
//    ROLE_ARTIST,
//    ROLE_ADVERTISEMENT_MANAGER,
//}


public enum ROLES {
    ROLE_ADMIN, ROLE_USER, ROLE_ADVERTISEMENT_MANAGER,ROLE,ROLE_ARTIST;

    public static ROLES fromString(String role) {
        if (role == null || role.isBlank()) {
            return ROLE_USER; // default
        }
        try {
            return ROLES.valueOf(role.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid role: " + role);
        }
    }
}

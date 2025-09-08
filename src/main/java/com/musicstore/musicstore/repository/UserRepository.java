package com.musicstore.musicstore.repository;

import com.musicstore.musicstore.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    User existsUserByEmail(String email);
}

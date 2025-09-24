package com.musicstore.musicstore.repository;

import com.musicstore.musicstore.model.User;
import com.musicstore.musicstore.model.ROLES;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    Boolean existsByEmail(String email);
    List<User> findByRole(ROLES role);
}
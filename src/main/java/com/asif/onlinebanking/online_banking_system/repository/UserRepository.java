package com.asif.onlinebanking.online_banking_system.repository;

import com.asif.onlinebanking.online_banking_system.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User>findByUsername(String username);
    Optional<User>findByEmail(String email);

    boolean existsByEmail(String email);
    boolean existsByUsername(String username);
}

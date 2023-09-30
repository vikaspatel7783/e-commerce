package com.ecommerce.eureka.gateway.userservice.repository;

import com.ecommerce.eureka.gateway.userservice.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findByEmail(String email);

    Optional<User> findByAuthToken(String authToken);
}

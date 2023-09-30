package com.ecommerce.eureka.gateway.userservice.service;

import com.ecommerce.eureka.gateway.userservice.entity.User;
import com.ecommerce.eureka.gateway.userservice.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User saveUser(User user) {
        return userRepository.save(user);
    }

    public Optional<User> findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public Optional<User> findUserByAuthToken(String authToken) {
        return userRepository.findByAuthToken(authToken);
    }
}

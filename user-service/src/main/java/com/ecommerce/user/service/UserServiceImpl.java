package com.ecommerce.user.service;

import com.ecommerce.user.entity.User;
import com.ecommerce.user.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User saveUser(User user) {
        return userRepository.save(user);
    }

    public Optional<User> findUser(String email) {
        return userRepository.findByEmail(email);
    }
}

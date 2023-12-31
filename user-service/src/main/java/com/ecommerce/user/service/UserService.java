package com.ecommerce.user.service;

import com.ecommerce.user.entity.User;

import java.util.Optional;

public interface UserService {
    User saveUser(User user);

    Optional<User> findUserByEmail(String email);

    Optional<User> findUserByAuthToken(String authToken);

}

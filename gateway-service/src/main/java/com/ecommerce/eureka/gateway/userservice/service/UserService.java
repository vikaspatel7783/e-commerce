package com.ecommerce.eureka.gateway.userservice.service;


import com.ecommerce.eureka.gateway.userservice.entity.User;

import java.util.Optional;

public interface UserService {
    User saveUser(User user);

    Optional<User> findUserByEmail(String email);

    Optional<User> findUserByAuthToken(String authToken);

}

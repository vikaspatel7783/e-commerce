package com.ecommerce.user.controller;

import com.ecommerce.user.dto.User;
import com.ecommerce.user.exception.InvalidSearchCriteriaException;
import com.ecommerce.user.exception.UserAlreadyExistException;
import com.ecommerce.user.service.UserService;
import jakarta.validation.Valid;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/users")
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/signup")
    public ResponseEntity<com.ecommerce.user.entity.User> signUp(@Valid @RequestBody User user) {
        if (userService.findUserByEmail(user.getEmail()).isPresent()) {
            throw new UserAlreadyExistException("User with this email already exist");
        }
        com.ecommerce.user.entity.User userEntity = new com.ecommerce.user.entity.User();
        userEntity.setEmail(user.getEmail());
        userEntity.setAuthToken(RandomStringUtils.random(30, true, true));

        return new ResponseEntity<>(userService.saveUser(userEntity), HttpStatus.CREATED);
    }

    // http://localhost:8085/users?email=?
    // http://localhost:8085/users?authToken=?
    @GetMapping("/retrieve")
    public ResponseEntity<com.ecommerce.user.entity.User> getUserBySearchCriteria(@RequestParam Map<String, String> criteriaParams) {
        if (criteriaParams.isEmpty()) {
            throw new InvalidSearchCriteriaException("Request does not have any search criteria");
        }

        String email = criteriaParams.get("email");
        if (email != null && !email.isEmpty()) {
            return ResponseEntity.ok(userService.findUserByEmail(email).orElse(null));
        }

        String authToken = criteriaParams.get("authToken");
        if (authToken != null && !authToken.isEmpty()) {
            return ResponseEntity.ok(userService.findUserByAuthToken(authToken).orElse(null));
        }

        throw new InvalidSearchCriteriaException("Request does not have valid search criteria");
    }

}

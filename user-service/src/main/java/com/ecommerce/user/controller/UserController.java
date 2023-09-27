package com.ecommerce.user.controller;

import com.ecommerce.user.dto.User;
import com.ecommerce.user.exception.UserAlreadyExistException;
import com.ecommerce.user.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Random;

@RestController
@RequestMapping("/users")
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/signup")
    public ResponseEntity<com.ecommerce.user.entity.User> signUp(@Valid @RequestBody User user) {
        if (userService.findUser(user.getEmail()).isPresent()) {
            throw new UserAlreadyExistException("User with this email already exist");
        }
        com.ecommerce.user.entity.User userEntity = new com.ecommerce.user.entity.User();
        userEntity.setEmail(user.getEmail());
        userEntity.setAuthToken(String.valueOf(new Random().nextLong()));

        return ResponseEntity.ok(userService.saveUser(userEntity));
    }

    @GetMapping("/{email}")
    public ResponseEntity<com.ecommerce.user.entity.User> getUser(@PathVariable("email") String email) {
        com.ecommerce.user.entity.User user = userService.findUser(email).orElse(null);
        return ResponseEntity.ok(user);
    }
}

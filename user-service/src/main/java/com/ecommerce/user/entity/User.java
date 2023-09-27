package com.ecommerce.user.entity;

import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor @NoArgsConstructor
@Getter @Setter
@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @Column(name = "auth_token", unique = true, nullable = false)
    private String authToken;
}

package com.ecommerce.eureka.gateway.feignclient;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class User {
    private long id;
    private String email;
    private String authToken;
}

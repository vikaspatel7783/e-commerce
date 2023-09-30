package com.ecommerce.eureka.gateway.service;

import com.ecommerce.eureka.gateway.feignclient.User;
import com.ecommerce.eureka.gateway.feignclient.UserServiceFeignApiClient;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.HashMap;

@Service
public class UserServiceCommunicator {

//    private UserServiceFeignApiClient userServiceFeignApiClient;
//
//    public UserServiceCommunicator(UserServiceFeignApiClient userServiceFeignApiClient) {
//        this.userServiceFeignApiClient = userServiceFeignApiClient;
//    }
//
//    public Mono<User> getUser(String authToken) {
//        HashMap<String, String> authTokenMap = new HashMap<>();
//        authTokenMap.put("authToken", authToken);
//        return (Mono<User>) userServiceFeignApiClient.getUserBySearchCriteria(authTokenMap)
//                .subscribe(user -> {
//                    Mono.just(user);
//                });
//    }
}

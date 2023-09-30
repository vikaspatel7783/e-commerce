package com.ecommerce.eureka.gateway.feignclient;

//import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import reactor.core.publisher.Mono;

import java.util.Map;

//@FeignClient(name = "USER-SERVICE")
public interface UserServiceFeignApiClient {

//    @GetMapping("/retrieve")
//    public Mono<User> getUserBySearchCriteria(@RequestParam Map<String, String> criteriaParams);
}

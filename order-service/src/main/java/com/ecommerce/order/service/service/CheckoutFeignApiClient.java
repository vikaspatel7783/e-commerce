package com.ecommerce.order.service.service;

import com.ecommerce.order.service.dto.CheckoutRequest;
import com.ecommerce.order.service.dto.CheckoutResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "CHECKOUT-SERVICE")
public interface CheckoutFeignApiClient {

    @PostMapping("/checkout/pay")
    CheckoutResponse checkout(@RequestBody CheckoutRequest checkoutRequest);
}

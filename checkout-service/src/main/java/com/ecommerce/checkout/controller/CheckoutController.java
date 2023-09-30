package com.ecommerce.checkout.controller;

import com.ecommerce.checkout.dto.CheckoutRequest;
import com.ecommerce.checkout.dto.CheckoutResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/checkout")
public class CheckoutController {

    @PostMapping("/pay")
    public ResponseEntity<CheckoutResponse> checkout(@RequestBody CheckoutRequest checkoutRequest) {
        CheckoutResponse checkoutResponse = new CheckoutResponse();
        checkoutResponse.setOrderId(checkoutRequest.getOrderId());
        checkoutResponse.setCheckoutId(UUID.randomUUID().toString());

        return ResponseEntity.ok(checkoutResponse);
    }
}

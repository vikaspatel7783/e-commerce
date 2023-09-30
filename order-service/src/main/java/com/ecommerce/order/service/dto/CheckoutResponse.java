package com.ecommerce.order.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CheckoutResponse {
    private String orderId;
    private String checkoutId;
}

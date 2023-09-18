package com.ecommerce.order.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConfirmOrderRequest {
    private long orderId;
    private OrderPaymentDetails orderPaymentDetails;
}

package com.ecommerce.order.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuotationResponse {
    private float unitPrice;
    private int quantity;
    private float discount;
    private float totalPrice;
    private float finalPrice;
}

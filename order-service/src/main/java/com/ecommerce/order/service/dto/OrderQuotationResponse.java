package com.ecommerce.order.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderQuotationResponse {
    private long id;
    private long productId;
    private String productName;
    private QuotationResponse quotationResponse;
}

package com.ecommerce.product.dto;

import lombok.Data;

import java.util.List;

@Data
public class ProductResponse {
    private List<Product> products;
}

package com.ecommerce.product.inventory.dto;

import lombok.Data;

import java.util.List;

@Data
public class ProductResponse {
    private List<ProductInventory> productInventories;
}

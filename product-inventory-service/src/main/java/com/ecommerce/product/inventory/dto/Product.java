package com.ecommerce.product.inventory.dto;

import lombok.Data;

@Data
public class Product extends ProductInventory{
    private float discountPercent;
}

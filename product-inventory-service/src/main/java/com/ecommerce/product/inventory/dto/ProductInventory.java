package com.ecommerce.product.inventory.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductInventory {
    private long id;

    @NotEmpty(message = "product name must not be empty")
    private String name;

    private float unitPrice;

    private boolean inStock;
}

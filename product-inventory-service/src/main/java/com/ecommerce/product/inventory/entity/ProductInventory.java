package com.ecommerce.product.inventory.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "PRODUCT_INVENTORY")
public class ProductInventory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String name;

    @Column(name = "price")
    private float unitPrice;

    @Column(name = "discount_percentage", columnDefinition = "float default 0")
    private float discountInPercentage = 0;

    @Column(name = "in_stock", columnDefinition = "boolean default false")
    private boolean inStock;
}

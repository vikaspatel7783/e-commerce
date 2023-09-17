package com.vikas.ecommerce.ordertrack.message;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Order {
    @Column(name = "order_id")
    private long id;

    @Column(name = "product_name", nullable = false)
    private String productName;

    @Column(name = "product_id", nullable = false)
    private long productId;

    @Column
    private int quantity;

    @Column(name = "price")
    private float unitPrice;

    @Column
    private float discount;

    @Column(nullable = false, name = "total_price")
    private float totalPrice;

    @Column(nullable = false, name = "final_price")
    private float finalPrice;

    @Column(nullable = false, name = "order_state")
    private String state;
}

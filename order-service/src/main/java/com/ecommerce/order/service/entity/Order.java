package com.ecommerce.order.service.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "ORDERS")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String productName;

    @Column(nullable = false)
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

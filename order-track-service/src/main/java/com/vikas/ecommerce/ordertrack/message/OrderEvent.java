package com.vikas.ecommerce.ordertrack.message;

import jakarta.persistence.*;
import lombok.*;

import java.text.SimpleDateFormat;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "order-track")
public class OrderEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    private String date;

    @Embedded
    private Order order;

    public OrderEvent(Order order) {
        this.order = order;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        date = simpleDateFormat.format(new Date());
    }
}

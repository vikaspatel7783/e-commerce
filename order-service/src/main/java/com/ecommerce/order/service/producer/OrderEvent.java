package com.ecommerce.order.service.producer;

import com.ecommerce.order.service.entity.Order;
import lombok.Data;

import java.text.SimpleDateFormat;
import java.util.Date;

@Data
public class OrderEvent {
    private String date;
    private Order order;
    private String checkoutId;

    public OrderEvent(Order order) {
        this.order = order;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        date = simpleDateFormat.format(new Date());
    }
}

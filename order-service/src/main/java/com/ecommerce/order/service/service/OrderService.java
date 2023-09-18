package com.ecommerce.order.service.service;

import com.ecommerce.order.service.entity.Order;

import java.util.Optional;

public interface OrderService {

    Order saveOrder(Order order);

    Optional<Order> getOrder(long orderId);
}

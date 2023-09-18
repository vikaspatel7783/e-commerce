package com.ecommerce.order.service.service;

import com.ecommerce.order.service.entity.Order;
import com.ecommerce.order.service.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService {

    private OrderRepository orderRepository;

    public OrderServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public Order saveOrder(Order order) {
        return orderRepository.save(order);
    }

    @Override
    public Optional<Order> getOrder(long orderId) {
        return orderRepository.findById(orderId);
    }
}

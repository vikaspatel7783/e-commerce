package com.vikas.ecommerce.ordertrack.service;

import com.vikas.ecommerce.ordertrack.message.OrderEvent;
import com.vikas.ecommerce.ordertrack.repository.OrderTrackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderTrackServiceImpl implements OrderTrackService {

    @Autowired
    private OrderTrackRepository orderTrackRepository;

    @Override
    public void save(OrderEvent orderEvent) {
        orderTrackRepository.save(orderEvent);
    }
}

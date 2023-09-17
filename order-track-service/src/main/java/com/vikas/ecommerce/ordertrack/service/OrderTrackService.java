package com.vikas.ecommerce.ordertrack.service;

import com.vikas.ecommerce.ordertrack.message.OrderEvent;

public interface OrderTrackService {

    void save(OrderEvent orderEvent);
}

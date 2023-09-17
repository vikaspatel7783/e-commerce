package com.vikas.ecommerce.ordertrack.consumer;

import com.vikas.ecommerce.ordertrack.message.OrderEvent;
import com.vikas.ecommerce.ordertrack.service.OrderTrackService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderEventConsumer {

    @Autowired
    OrderTrackService orderTrackService;

    @RabbitListener(queues = "${rabbitmq.order-track.queue}")
    public void consumeOrderTrackMessage(OrderEvent orderEvent) {
        orderTrackService.save(orderEvent);
    }

}

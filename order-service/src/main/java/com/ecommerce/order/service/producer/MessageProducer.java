package com.ecommerce.order.service.producer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class MessageProducer {

    private static final Logger LOGGER = LoggerFactory.getLogger(MessageProducer.class);

    @Value("${rabbitmq.order-track.exchange}")
    private String exchange;

    @Value("${rabbitmq.order-track.binding.routing.key}")
    private String routingKey;

    private RabbitTemplate rabbitTemplate;

    public MessageProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendOrderMessage(OrderEvent orderEvent) {
        rabbitTemplate.convertAndSend(exchange, routingKey, orderEvent);
        LOGGER.info(String.format("Order track event sent: %s", orderEvent));
    }
}

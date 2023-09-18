package com.ecommerce.order.service.exception;

public class OrderException extends RuntimeException {

    private String message;

    public OrderException(String message) {
        super(message);
        this.message = message;
    }
}

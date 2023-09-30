package com.ecommerce.order.service.exception;

public class CheckoutException extends RuntimeException {

    private String message;

    public CheckoutException(String message) {
        super(message);
        this.message = message;
    }
}

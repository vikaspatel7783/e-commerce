package com.ecommerce.order.service.exception;

public class ProductException extends RuntimeException {

    private String message;

    public ProductException(String message) {
        super(message);
        this.message = message;
    }
}

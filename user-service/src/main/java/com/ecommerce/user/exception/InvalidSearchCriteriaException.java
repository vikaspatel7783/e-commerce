package com.ecommerce.user.exception;

public class InvalidSearchCriteriaException extends RuntimeException {

    private String message;

    public InvalidSearchCriteriaException(String message) {
        super(message);
        this.message = message;
    }
}

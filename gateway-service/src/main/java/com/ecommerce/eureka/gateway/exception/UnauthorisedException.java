package com.ecommerce.eureka.gateway.exception;

import org.springframework.http.HttpStatusCode;
import org.springframework.web.server.ResponseStatusException;

public class UnauthorisedException extends ResponseStatusException {

    public UnauthorisedException(HttpStatusCode status) {
        super(status);
    }

    public UnauthorisedException(HttpStatusCode status, String reason) {
        super(status, reason);
    }

}
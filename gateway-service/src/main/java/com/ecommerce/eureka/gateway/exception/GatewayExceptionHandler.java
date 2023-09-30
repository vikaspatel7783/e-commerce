package com.ecommerce.eureka.gateway.exception;

import org.springframework.boot.web.reactive.error.ErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.reactive.result.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@ControllerAdvice
public class GatewayExceptionHandler extends ResponseEntityExceptionHandler {

//    @ExceptionHandler(UnauthorizedException.class)
//    public Mono<ServerResponse> handleIllegalState(ServerWebExchange exchange, UnauthorizedException exc) {
//        exchange.getAttributes().putIfAbsent(ErrorAttributes.ERROR_ATTRIBUTE, exc);
//        return ServerResponse.from(ErrorResponse.builder(exc, HttpStatus.FORBIDDEN, exc.getMessage()).build());
//    }

}
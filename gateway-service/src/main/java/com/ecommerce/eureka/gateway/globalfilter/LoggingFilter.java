package com.ecommerce.eureka.gateway.globalfilter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class LoggingFilter implements GlobalFilter, Ordered {

    private final Logger LOGGER = LoggerFactory.getLogger(LoggingFilter.class);
    private static final int HIGHEST_ORDER = -1;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        logRequest(exchange);
        return chain.filter(exchange)
                .then(Mono.fromRunnable(() -> {
                    logResponse(exchange);
                }));
    }


    private void logRequest(ServerWebExchange exchange) {
        ServerHttpRequest request = exchange.getRequest();
        LOGGER.info("------- Request at LoggingFilter -------");
        LOGGER.info("Request Path: " + request.getPath());
        LOGGER.info("Request QueryParams: "+request.getQueryParams());
        LOGGER.info("Request Headers: "+ request.getHeaders());
    }

    private void logResponse(ServerWebExchange exchange) {
        ServerHttpResponse response = exchange.getResponse();
        LOGGER.info("-------- Response at LoggingFilter --------");
        LOGGER.info("Response Status code: " + response.getStatusCode());
    }

    @Override
    public int getOrder() {
        return HIGHEST_ORDER;
    }
}

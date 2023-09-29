package com.ecommerce.eureka.gateway.globalfilter;

import com.ecommerce.eureka.gateway.exception.UnauthorisedException;
import com.google.common.base.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
public class AuthenticationFilter implements GlobalFilter, Ordered {

    private final Logger LOGGER = LoggerFactory.getLogger(AuthenticationFilter.class);
    private static final int SECOND_HIGHEST_ORDER = 0;

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
        LOGGER.info("-------- Request at AuthenticationFilter -------");
        List<String> authorization = request.getHeaders().get("Authorization");
        if (authorization == null || authorization.isEmpty()) {
            throw new UnauthorisedException(HttpStatus.UNAUTHORIZED, "Authorization token not found");
        }
        String authToken = authorization.get(0);
        LOGGER.info("Authorization header: "+ authToken);
    }

    private void logResponse(ServerWebExchange exchange) {
        ServerHttpResponse response = exchange.getResponse();
        LOGGER.info("--------- Response at AuthenticationFilter --------");
        LOGGER.info("Response Status code: " + response.getStatusCode());
    }

    @Override
    public int getOrder() {
        return SECOND_HIGHEST_ORDER;
    }
}

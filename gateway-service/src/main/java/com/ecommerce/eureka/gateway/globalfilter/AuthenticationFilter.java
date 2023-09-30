package com.ecommerce.eureka.gateway.globalfilter;

import com.ecommerce.eureka.gateway.exception.UnauthorizedException;
import com.ecommerce.eureka.gateway.userservice.service.UserService;
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

import java.util.List;

@Component
public class AuthenticationFilter implements GlobalFilter, Ordered {

    private final Logger LOGGER = LoggerFactory.getLogger(AuthenticationFilter.class);
    private static final int SECOND_HIGHEST_ORDER = 0;

//    private UserServiceCommunicator userServiceCommunicator;
//
//    public AuthenticationFilter(@Lazy UserServiceCommunicator userServiceCommunicator) {
//        this.userServiceCommunicator = userServiceCommunicator;
//    }

    private UserService userService;

    public AuthenticationFilter(UserService userService) {
        this.userService = userService;
    }

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

        if (checkSkipFilter(request)) {
            return;
        }

        List<String> authorization = request.getHeaders().get("authToken");
        if (authorization == null || authorization.isEmpty()) {
            LOGGER.info("Authorization token not found in request header");
            throwUnauthorizedException("Authorization token not found in request header");
        }
        String authToken = authorization.get(0);
        LOGGER.info("Authorization header: " + authToken);

        authenticateToken(authToken);
    }

    private void throwUnauthorizedException(String message) {
        throw new UnauthorizedException(message);
    }

    private void logResponse(ServerWebExchange exchange) {
        ServerHttpResponse response = exchange.getResponse();
        LOGGER.info("--------- Response at AuthenticationFilter --------");
        LOGGER.info("Response Status code: " + response.getStatusCode());
    }

    private void authenticateToken(String token) {
//        User user = userServiceCommunicator.getUser(token);
//        if (!user.getAuthToken().equals(token)) {
//            throwUnauthorizedException();
//        }
//        userServiceCommunicator.getUser(token).subscribe(user -> {
//            if (!user.getAuthToken().equals(token)) {
//                throwUnauthorizedException();
//            }
//        });

        userService.findUserByAuthToken(token)
                .orElseThrow(() -> {
                            LOGGER.info("Invalid authToken supplied");
                            throwUnauthorizedException("Invalid authToken supplied");
                            return null;
                        }
                );
        LOGGER.info("AuthToken successfully matched.");
    }

    private boolean checkSkipFilter(ServerHttpRequest request) {
        if (request.getPath().toString().equals("/gateway/users/signup")) {
            LOGGER.info("Skipping filter for /gateway/users/signup path");
            return true;
        }
        return false;
    }

    @Override
    public int getOrder() {
        return SECOND_HIGHEST_ORDER;
    }
}

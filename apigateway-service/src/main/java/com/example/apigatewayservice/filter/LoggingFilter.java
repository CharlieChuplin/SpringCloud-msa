package com.example.apigatewayservice.filter;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.OrderedGatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

// yml 필터로 사용
@Component
@Slf4j
public class LoggingFilter extends AbstractGatewayFilterFactory<LoggingFilter.Config> {
    public LoggingFilter() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        GatewayFilter filter = new OrderedGatewayFilter((exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();
            ServerHttpResponse response = exchange.getResponse();

            log.info("***Logging Filter baseMessage: {}", config.getBaseMessage());

            if (config.isPreLogger()) {
                log.info("Logging Filter Start: request id -> {}", request.getId());
            }
            // Custom Post Filter : 사후 필터
            return chain.filter(exchange).then(Mono.fromRunnable(() -> { // Mono -> 비동기 방식 단일 값 전달할 때 사용
                if (config.isPostLogger()) {
                    log.info("Logging Filter End: response id ? -> {}", response.getStatusCode());
                }
            }));
        }, Ordered.LOWEST_PRECEDENCE);


        return filter;
//        return (exchange, chain) -> {
//            ServerHttpRequest request = exchange.getRequest();
//            ServerHttpResponse response = exchange.getResponse();
//
//            log.info("Logging Filter baseMessage: {}", config.getBaseMessage());
//
//            if (config.isPreLogger()) {
//                log.info("Logging Filter Start: request id -> {}", request.getId());
//            }
//            // Custom Post Filter : 사후 필터
//            return chain.filter(exchange).then(Mono.fromRunnable(() -> { // Mono -> 비동기 방식 단일 값 전달할 때 사용
//                if (config.isPostLogger()) {
//                    log.info("Logging Filter End: response id ? -> {}", response.getStatusCode());
//                }
//            }));
//        };
    }

    @Data
    public static class Config {
        private String baseMessage;
        private boolean preLogger;
        private boolean postLogger;
    }

}

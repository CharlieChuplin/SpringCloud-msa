package com.example.apigatewayservice.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

// yml 필터로 사용
@Component
@Slf4j
public class CustomFilter extends AbstractGatewayFilterFactory<CustomFilter.Config> {
    public CustomFilter() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        // Custom Pre Filter : 사전 필터
        return (exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();
            ServerHttpResponse response = exchange.getResponse();

            log.info("Custom PRE Filter: request id ? -> {}", request.getId());

            // Custom Post Filter : 사후 필터
            return chain.filter(exchange).then(Mono.fromRunnable(() -> { // Mono -> 비동기 방식 단일 값 전달할 때 사용
                log.info("Custom POST Filter: response id ? -> {}", response.getStatusCode());
            }));
        };
    }

    public static class Config {

    }

}

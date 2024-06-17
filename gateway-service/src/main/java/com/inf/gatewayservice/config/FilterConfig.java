package com.inf.gatewayservice.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//@Configuration
public class FilterConfig {

//    @Bean
    public RouteLocator gatewayRoutes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route(r -> r.path("/user-service/**")
                            .filters(f -> f.addRequestHeader("user-request", "user-request-header")
                                            .addResponseHeader("user-response", "user-response-header"))
                            .uri("lb://user-service"))
                .build();
    }

}

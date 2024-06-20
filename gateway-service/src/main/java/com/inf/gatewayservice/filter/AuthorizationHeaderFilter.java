package com.inf.gatewayservice.filter;

import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHeaders;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;

@Component
@Slf4j
@RequiredArgsConstructor
public class AuthorizationHeaderFilter extends AbstractGatewayFilterFactory<AuthorizationHeaderFilter.Config> {
    private final Environment env;

    /**
     * login -> token -> users(with token) -> header(include token)
     */
    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();

            if(!request.getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
                return onError(exchange, "no authorization header", HttpStatus.UNAUTHORIZED);
            }


            // Bearer token
            String authorizationHeader = request.getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);
            String token = authorizationHeader.replace("Bearer", "");

            if(!isJwtValid(token)) {
                return onError(exchange, "JWT Token is not valid", HttpStatus.UNAUTHORIZED); // 401
            }

            return chain.filter(exchange);
        };
    }

    private boolean isJwtValid(String token) {
        boolean isValid = true;

        String subject = null;
        try {
            subject = Jwts.parser().setSigningKey(getSignKey())
                    .parseClaimsJws(token).getBody()
                    .getSubject();
        } catch(Exception e) {
            isValid = false;
        }

        if(subject == null || subject.isEmpty()) isValid = false;

        return isValid;
    }

    private SecretKey getSignKey() {
        byte[] keyBytes = env.getProperty("token.secret").getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    private Mono<Void> onError(ServerWebExchange exchange, String err, HttpStatus status) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(status);

        log.error(err);
        return response.setComplete();
    }


    public static class Config {

    }

}

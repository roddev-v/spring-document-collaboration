package com.roddevv;

import com.roddevv.dto.TokenDto;
import com.roddevv.dto.UserDto;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Component
public class AuthGatewayFilter extends AbstractGatewayFilterFactory<AuthGatewayFilter.Config> {
    private final WebClient.Builder webClientBuilder;

    public AuthGatewayFilter(WebClient.Builder webClientBuilder) {
        super(Config.class);
        this.webClientBuilder = webClientBuilder;
    }

    @Override
    public GatewayFilter apply(AuthGatewayFilter.Config config) {
        return ((exchange, chain) -> {
            final ServerHttpRequest request = exchange.getRequest();
            final HttpHeaders headers = request.getHeaders();

            if (!headers.containsKey(HttpHeaders.AUTHORIZATION)) {
                exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                return exchange.getResponse().setComplete();
            }

            final String authHeader = Objects.requireNonNull(headers.get(HttpHeaders.AUTHORIZATION)).get(0);
            final String[] parts = authHeader.split(" ");

            if (parts.length != 2 || !"Bearer".equals(parts[0])) {
                exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                return exchange.getResponse().setComplete();
            }
            final Map<String, String> tokenCheckBody = new HashMap<>();
            tokenCheckBody.put("token", parts[1]);

            return webClientBuilder
                    .build()
                    .post()
                    .uri("http://auth-service/auth/check-token")
                    .body(BodyInserters.fromValue(tokenCheckBody))
                    .retrieve()
                    .bodyToMono(TokenDto.class)
                    .map(tokenDto -> {
                        if (tokenDto.isExpired()) {
                            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Unauthorized");
                        }
                        final UserDto userDto = tokenDto.getUser();
                        exchange.getRequest()
                                .mutate()
                                .header("X-auth-user-id", String.valueOf(userDto.getId()))
                                .header("X-auth-user-email", String.valueOf(userDto.getEmail()))
                                .header("X-auth-user-nickname", String.valueOf(userDto.getNickname()));
                        return exchange;
                    })
                    .flatMap(chain::filter);
        });
    }

    public static class Config {

    }
}
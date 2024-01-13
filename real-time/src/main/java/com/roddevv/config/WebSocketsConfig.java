package com.roddevv.config;

import com.roddevv.interceptors.SessionHandshakeInterceptor;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;

@Configuration
@EnableWebSocketMessageBroker
@AllArgsConstructor
public class WebSocketsConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker("/events");
        config.setApplicationDestinationPrefixes("/app");
        config.setUserDestinationPrefix("/app");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/rtc")
                .setAllowedOriginPatterns("*")
                .addInterceptors(new SessionHandshakeInterceptor());

        registry.addEndpoint("/rtc")
                .setAllowedOriginPatterns("*")
                .addInterceptors(new SessionHandshakeInterceptor())
                .withSockJS();
    }
}
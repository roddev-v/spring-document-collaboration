package com.roddevv.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.stereotype.Component;

@Component
public class WebSocketsInterceptor implements ChannelInterceptor {

    @Autowired
    private MetricsService metricsService;

    private static final Logger logger = LoggerFactory.getLogger(WebSocketsInterceptor.class);

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
        if (StompCommand.CONNECT.equals(accessor.getCommand())) {
            metricsService.incrementConnections();
            logger.info("WebSocket connection established. Session ID: {}", accessor.getSessionId());
        } else if (StompCommand.DISCONNECT.equals(accessor.getCommand())) {
            metricsService.decrementConnections();
            logger.info("WebSocket connection closed. Session ID: {}", accessor.getSessionId());
        }
        return message;
    }
}
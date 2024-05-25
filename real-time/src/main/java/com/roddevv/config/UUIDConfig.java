package com.roddevv.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.UUID;

@Configuration
public class UUIDConfig {

    @Bean
    public String uuid() {
        return "document-editing-" + UUID.randomUUID().toString();
    }
}

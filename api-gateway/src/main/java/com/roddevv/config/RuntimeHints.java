package com.roddevv.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportRuntimeHints;

@Configuration
@ImportRuntimeHints(AuthGatewayFilterHints.class)
public class RuntimeHints {
}


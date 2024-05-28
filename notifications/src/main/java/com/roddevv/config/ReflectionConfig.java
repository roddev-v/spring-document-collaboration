package com.roddevv.config;

import com.roddevv.dto.NotificationDtoRuntimeHints;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportRuntimeHints;

@Configuration
@ImportRuntimeHints({NotificationDtoRuntimeHints.class})
public class ReflectionConfig {
}

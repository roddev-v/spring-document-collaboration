package com.roddevv.config;

import com.roddevv.dto.EditingEventDtoRuntimeHints;
import com.roddevv.dto.NotificationDtoHints;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportRuntimeHints;

@Configuration
@ImportRuntimeHints({NotificationDtoHints.class, EditingEventDtoRuntimeHints.class})
public class ReflectionConfig {
}

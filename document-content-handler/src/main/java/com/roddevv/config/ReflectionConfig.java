package com.roddevv.config;

import com.roddevv.dto.EditingEventDtoRuntimeHints;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportRuntimeHints;

@Configuration
@ImportRuntimeHints({EditingEventDtoRuntimeHints.class})
public class ReflectionConfig {
}

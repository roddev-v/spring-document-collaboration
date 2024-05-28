package com.roddevv.config;

import com.roddevv.dto.ClientEventDtoRuntimeHints;
import com.roddevv.dto.EditingEventDtoRuntimeHints;
import com.roddevv.dto.EventBroadcastDtoRuntimeHints;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportRuntimeHints;

@Configuration
@ImportRuntimeHints({
        EventBroadcastDtoRuntimeHints.class,
        ClientEventDtoRuntimeHints.class,
        EditingEventDtoRuntimeHints.class,
})
public class ReflectionConfig {
}

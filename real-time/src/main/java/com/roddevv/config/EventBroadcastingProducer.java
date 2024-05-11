package com.roddevv.config;

import com.roddevv.dto.EventBroadcastDto;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
@AllArgsConstructor
@NoArgsConstructor
public class EventBroadcastingProducer {

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;

    @Bean
    public KafkaTemplate<String, EventBroadcastDto> eventBroadcastKafkaTemplate() {
        final Map<String, Object> props = new HashMap<>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        final ProducerFactory<String, EventBroadcastDto> producer = new DefaultKafkaProducerFactory<>(props);
        return new KafkaTemplate<>(producer);
    }
}
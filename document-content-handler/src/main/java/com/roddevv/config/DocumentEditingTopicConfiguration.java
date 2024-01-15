package com.roddevv.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class DocumentEditingTopicConfiguration {
    @Bean
    public NewTopic documentEditingTopic() {
        return TopicBuilder.name("document-editing").build();
    }
}

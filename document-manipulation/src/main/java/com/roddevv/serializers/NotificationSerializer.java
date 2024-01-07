package com.roddevv.serializers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.roddevv.dto.NotificationDto;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Serializer;

import java.util.Map;

public class NotificationSerializer implements Serializer<NotificationDto> {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public byte[] serialize(String topic, NotificationDto data) {
        try {
            if (data == null) {
                System.out.println("Null received at serializing");
                return null;
            }
            return objectMapper.writeValueAsBytes(data);
        } catch (Exception e) {
            throw new SerializationException("Error when serializing NotificationDto to byte[]");
        }
    }
}
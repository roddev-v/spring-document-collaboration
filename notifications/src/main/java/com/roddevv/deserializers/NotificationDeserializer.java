package com.roddevv.deserializers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.roddevv.dtos.NotificationRequestDto;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Deserializer;

import java.nio.charset.StandardCharsets;

@Slf4j
public class NotificationDeserializer implements Deserializer<NotificationRequestDto> {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public NotificationRequestDto deserialize(String topic, byte[] data) {
        try {
            if (data == null) {
                System.out.println("Null received at deserializing");
                return null;
            }
            System.out.println("Deserializing...");
            return objectMapper.readValue(new String(data, StandardCharsets.UTF_8), NotificationRequestDto.class);
        } catch (Exception e) {
            throw new SerializationException("Error when deserializing byte[] to MessageDto");
        }
    }
}

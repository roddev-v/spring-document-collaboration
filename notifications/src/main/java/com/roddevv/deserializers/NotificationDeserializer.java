package com.roddevv.deserializers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.roddevv.dto.NotificationDto;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Deserializer;

@Slf4j
public class NotificationDeserializer implements Deserializer<NotificationDto> {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public NotificationDto deserialize(String topic, byte[] data) {
        try {
            if (data == null) {
                System.out.println("Null received at deserializing");
                return null;
            }
            System.out.println("Deserializing...");
            return objectMapper.readValue(data, NotificationDto.class);
        } catch (Exception e) {
            throw new SerializationException("Error when deserializing byte[] to MessageDto");
        }
    }
}

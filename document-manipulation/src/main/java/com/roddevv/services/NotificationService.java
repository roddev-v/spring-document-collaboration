package com.roddevv.services;

import com.roddevv.dto.NotificationDto;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class NotificationService {
    @Autowired
    private KafkaTemplate<String, NotificationDto> notificationsTemplate;

    public void send(NotificationDto dto) {
        this.notificationsTemplate.send("notifications", dto);
    }
}

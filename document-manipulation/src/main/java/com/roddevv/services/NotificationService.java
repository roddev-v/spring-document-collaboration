package com.roddevv.services;

import com.roddevv.dto.NotificationDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {
    @Autowired
    private KafkaTemplate<String, NotificationDto> notificationsTemplate;

    public void send(Long senderId,
                     String senderEmail,
                     String senderNickname,
                     Long recipientId,
                     String type) {
        final NotificationDto dto = NotificationDto.builder()
                .senderId(senderId)
                .senderEmail(senderEmail)
                .senderNickname(senderNickname)
                .recipientId(recipientId)
                .type(type)
                .build();
        this.notificationsTemplate.send("notifications", dto);
    }
}

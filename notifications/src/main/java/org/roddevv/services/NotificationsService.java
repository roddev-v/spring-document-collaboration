package org.roddevv.services;

import org.roddevv.dtos.NotificationDto;
import org.roddevv.entities.Notification;
import org.roddevv.repositories.NotificationsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationsService {

    @Autowired
    private NotificationsRepository repository;

    @KafkaListener(topics = "notifications", groupId = "notifications-group-di")
    private void save(NotificationDto dto) {
        final String title;
        final String content;
        if (dto.getType().equals("invite")) {
            title = "New invitation!";
            content = String.format("%S invited you to join a collaboration session", dto.getSenderEmail());
        } else {
            title = "Revoked";
            content = String.format("%S invited you to join a collaboration session", dto.getSenderEmail());
        }
        final Notification notification = Notification.builder()
                .recipientId(dto.getRecipientId())
                .senderId(dto.getSenderId())
                .senderEmail(dto.getSenderEmail())
                .type(dto.getType())
                .title(title)
                .content(content)
                .read(false)
                .build();
        repository.save(notification);
    }

    private List<Notification> getUnread(Long recipientId) {
        return repository.findAllByRecipientId(recipientId).stream().filter(n -> !n.getRead()).toList();
    }
}

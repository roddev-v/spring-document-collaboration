package com.roddevv.services;

import com.roddevv.dto.NotificationDto;
import com.roddevv.entities.Notification;
import com.roddevv.repositories.NotificationsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
public class NotificationsService {

    @Autowired
    private NotificationsRepository notificationsRepository;

    private static final Logger logger = LoggerFactory.getLogger(NotificationsService.class);

    private final Map<Long, SseEmitter> emitters = new HashMap<>();

    public List<Notification> getAllUnread(Long userId) {
        return this.notificationsRepository.findUnreadForUser(userId);
    }

    public List<Notification> getALlRead(Long userId) {
        return this.notificationsRepository.findAllReadForUser(userId);
    }

    public void readAll(Long userId) {
        this.notificationsRepository.markAsReadForUser(userId);
    }

    public void subscribe(Long id, SseEmitter emitter) {
        this.emitters.put(id, emitter);
        emitter.onError((e) -> this.emitters.remove(id));
        emitter.onCompletion(() -> this.emitters.remove(id));
    }

    private void send(Long id, Notification notification) {
        logger.info("Sending notification: " + notification.toString());
        final SseEmitter emitter = this.emitters.get(id);
        if (!Objects.nonNull(emitter)) {
            return;
        }
        try {
            emitter.send(notification);
            logger.info("Notification sent: " + notification.getId());
            notification.setDelivered(true);
        } catch (Exception e) {
            logger.info("Notification not sent via SSE: " + notification.getId());
            notification.setDelivered(false);
        }
        this.notificationsRepository.save(notification);
    }

    @KafkaListener(topics = "notifications", groupId = "notifications-group-id")
    private void sendNotification(@Payload NotificationDto dto) {
        logger.info("Consuming notification from Kafka: " + dto.toString());
        final Notification notification = new Notification(dto);
        this.send(dto.getRecipientId(), notification);
    }
}

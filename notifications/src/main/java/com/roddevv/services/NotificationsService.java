package com.roddevv.services;

import com.roddevv.dto.NotificationDto;
import com.roddevv.entities.Notification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Service
public class NotificationsService {
    private static final Logger logger = LoggerFactory.getLogger(NotificationsService.class);

    private final Map<Long, SseEmitter> emitters = new HashMap<>();

    public void subscribe(Long id, SseEmitter emitter) {
        this.emitters.put(id, emitter);
        emitter.onError((e) -> this.emitters.remove(id));
        emitter.onCompletion(() -> this.emitters.remove(id));
    }

    private boolean send(Long id, Notification notification) {
        logger.info("Sending notification: " + notification.toString());
        final SseEmitter emitter = this.emitters.get(id);
        if (!Objects.nonNull(emitter)) {
            return false;
        }
        try {
            emitter.send(notification);
            logger.info("Notification sent: " + notification.getId());
            return true;
        } catch (Exception e) {
            logger.info("Notification not sent: " + notification.getId());
            return false;
        }
    }

    @KafkaListener(topics = "notifications", groupId = "notifications-group-id")
    private void sendNotification(@Payload NotificationDto dto) {
        logger.info("Consuming notification: " + dto.toString());
        final boolean isSubscribed = this.emitters.get(dto.getRecipientId()) != null;
        final Notification notification = new Notification(dto);

        if (isSubscribed) {
            final boolean delivered = this.send(dto.getRecipientId(), notification);
            logger.info("Notification sent (Kafka): " + notification.getId());
            notification.setDelivered(delivered);
        } else {
            logger.info("Notification not sent (Kafka): " + notification.getId());
            notification.setDelivered(false);
        }
    }
}

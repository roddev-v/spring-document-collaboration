package com.roddevv.services;

import com.roddevv.dto.NotificationDto;
import com.roddevv.entities.Notification;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Service
public class NotificationsService {

    private final Map<Long, SseEmitter> emitters = new HashMap<>();

    public void subscribe(Long id, SseEmitter emitter) {
        this.emitters.put(id, emitter);
        emitter.onError((e) -> this.emitters.remove(id));
        emitter.onCompletion(() -> this.emitters.remove(id));
    }

    private boolean send(Long id, Notification notification) {
        final SseEmitter emitter = this.emitters.get(id);
        if (!Objects.nonNull(emitter)) {
            return false;
        }
        try {
            emitter.send(notification);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @KafkaListener(topics = "notifications", groupId = "notifications-group-id")
    private void sendNotification(@Payload NotificationDto dto) {
        final boolean isSubscribed = this.emitters.get(dto.getRecipientId()) != null;
        final Notification notification = new Notification(dto);

        if (isSubscribed) {
            final boolean delivered = this.send(dto.getRecipientId(), notification);
            notification.setDelivered(delivered);
        } else {
            notification.setDelivered(false);
        }
    }
}

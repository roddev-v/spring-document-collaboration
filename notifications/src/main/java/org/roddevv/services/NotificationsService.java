package org.roddevv.services;

import org.roddevv.models.Notification;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class NotificationsService {
    private final Map<String, SseEmitter> emitters = new ConcurrentHashMap<>();

    public void subscribe(String uuid, SseEmitter emitter) {
        emitters.put(uuid, emitter);
        emitter.onCompletion(() -> emitters.remove(uuid));
        emitter.onError((e) -> emitters.remove(uuid));
    }

    public void send(Notification notification) {
        final SseEmitter emitter = emitters.get(notification.getRecipientUuid());
        if (emitter == null) {
            return;
        }
        try {
            emitter.send(notification);
        } catch (Exception e) {
            emitter.complete();
        }
    }
}

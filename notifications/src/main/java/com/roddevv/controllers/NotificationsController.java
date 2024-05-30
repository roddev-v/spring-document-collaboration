package com.roddevv.controllers;

import com.roddevv.entities.Notification;
import lombok.AllArgsConstructor;
import com.roddevv.services.NotificationsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.List;

@RestController
@RequestMapping("notifications")
@AllArgsConstructor
public class NotificationsController {

    @Autowired
    private NotificationsService service;

    @GetMapping(path = "/subscribe", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter subscribe(
            @RequestHeader("X-auth-user-id") Long id
    ) {
        final SseEmitter emitter = new SseEmitter(Long.MAX_VALUE);
        this.service.subscribe(id, emitter);
        return emitter;
    }

    @GetMapping
    public ResponseEntity<List<Notification>> getNotifications(@RequestHeader("X-auth-user-id") Long userId) {
        List<Notification> notifications = service.getAllUnread(userId);
        return ResponseEntity.ok(notifications);
    }

    @GetMapping("/read")
    public ResponseEntity<List<Notification>> getReadNotifications(@RequestHeader("X-auth-user-id") Long userId) {
        List<Notification> notifications = service.getALlRead(userId);
        return ResponseEntity.ok(notifications);
    }

    @PostMapping("/read-all")
    public void readAll(@RequestHeader("X-auth-user-id") Long userId) {
        service.readAll(userId);
    }
}

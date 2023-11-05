package org.roddevv.controllers;

import lombok.AllArgsConstructor;
import org.roddevv.entities.Notification;
import org.roddevv.services.NotificationsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
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

    @GetMapping("/unread")
    public List<Notification> getUnread(
            @RequestHeader("X-auth-user-id") Long id
    ) {
        return this.service.getUnread(id);
    }

    @PostMapping("/read-all")
    public void readAll(@RequestHeader("X-auth-user-id") Long id) {
        this.service.readAll(id);
    }
}

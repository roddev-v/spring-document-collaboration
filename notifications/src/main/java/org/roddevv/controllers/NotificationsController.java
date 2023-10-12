package org.roddevv.controllers;

import org.roddevv.services.NotificationsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@RestController
@RequestMapping("/notifications")
public class NotificationsController {

    @Autowired
    private NotificationsService service;

    @GetMapping(path = "/subscribe/{uid}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter subscribe(@PathVariable String uid) {
        final SseEmitter emitter = new SseEmitter(Long.MAX_VALUE);
        this.service.subscribe(uid, emitter);
        return emitter;
    }
}

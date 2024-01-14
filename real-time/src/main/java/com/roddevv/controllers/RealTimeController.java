package com.roddevv.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.util.Map;

@Controller
public class RealTimeController {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/events/{documentId}")
    public void sendMessage(@DestinationVariable String documentId, Map<String, Object> event) {
        messagingTemplate.convertAndSend("/events/updates/" + documentId, event);
    }
}

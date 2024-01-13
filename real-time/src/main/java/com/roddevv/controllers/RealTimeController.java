package com.roddevv.controllers;

import com.roddevv.dto.EventDto;
import com.roddevv.dto.EventType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class RealTimeController {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/events/{documentId}")
    public String sendMessage(@DestinationVariable String documentId, EventDto event) {
        if (event.getType() == EventType.USER_JOINED) {
            messagingTemplate.convertAndSend("/events/updates/" + documentId, "User jooined");
        }
        return "Test";
    }
}

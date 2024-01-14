package com.roddevv.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class RealTimeController {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/events/{documentId}")
    public String sendMessage(@DestinationVariable String documentId, List<Object> event) {
        // TODOs
        // 1. Emit the message to the respective channel
        // 2. Do a local snapshot of the data

        messagingTemplate.convertAndSend("/events/updates/" + documentId, event);
        return "Test";
    }
}

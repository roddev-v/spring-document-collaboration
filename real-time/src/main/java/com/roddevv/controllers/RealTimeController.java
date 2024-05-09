package com.roddevv.controllers;

import com.roddevv.dto.ClientEventDto;
import com.roddevv.services.RTCService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
@AllArgsConstructor
public class RealTimeController {

    @Autowired
    private RTCService rtcService;
    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/events/{documentId}")
    public void sendMessage(@DestinationVariable String documentId, ClientEventDto event) {
        messagingTemplate.convertAndSend("/events/updates/" + documentId, event);
        rtcService.handleEvent(event);
    }
}

package com.roddevv.controllers;

import com.roddevv.dto.ClientEventDto;
import com.roddevv.dto.EventBroadcastDto;
import com.roddevv.services.RTCService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class RealTimeController {

    private static final Logger logger = LoggerFactory.getLogger(RealTimeController.class);

    @Autowired
    private RTCService rtcService;
    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Autowired
    private String uuid;

    @MessageMapping("/events/{documentId}")
    public void sendMessage(@DestinationVariable String documentId, ClientEventDto event) {
        logger.info(String.format("Receiving editing event for documentId %s from userId %s", event.getDocumentId(), event.getUserId()));

        messagingTemplate.convertAndSend("/events/updates/" + documentId, event);
        rtcService.handleEvent(event);
    }

    @KafkaListener(topics = "document-editing-broadcast", groupId = "#{@uuid}")
    public void broadcastEvent(EventBroadcastDto eventBroadcastDto) {
        final ClientEventDto event = eventBroadcastDto.getEvent();

        logger.info(String.format("Broadcasting editing event for documentId %s from userId %s", event.getDocumentId(), event.getUserId()));
        messagingTemplate.convertAndSend("/events/updates/" + event.getDocumentId(), event);
    }
}

package com.roddevv.controllers;

import com.roddevv.dto.ClientEventDto;
import com.roddevv.dto.EditingEventDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class RealTimeController {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Autowired
    private KafkaTemplate<String, EditingEventDto> documentEditingEvents;

    @MessageMapping("/events/{documentId}")
    public void sendMessage(@DestinationVariable String documentId, ClientEventDto event) {
        messagingTemplate.convertAndSend("/events/updates/" + documentId, event);

        final EditingEventDto dto = EditingEventDto.builder()
                .documentId(documentId)
                .content(event.getContent())
                .eventType(event.getType())
                .build();
        documentEditingEvents.send("document-editing", dto);
    }
}

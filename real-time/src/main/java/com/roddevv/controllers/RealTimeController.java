package com.roddevv.controllers;

import com.roddevv.dto.ClientEventDto;
import com.roddevv.dto.EditingEventDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class RealTimeController {

    final Logger logger = LoggerFactory.getLogger(RealTimeController.class);

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Autowired
    private KafkaTemplate<String, EditingEventDto> documentEditingEvents;

    @Value("${HOSTNAME}")
    private String hostname;

    @MessageMapping("/events/{documentId}")
    public void sendMessage(@DestinationVariable String documentId, ClientEventDto event) {
        logger.debug(String.format("Broadcasting document %s on host %s", documentId, hostname));
        messagingTemplate.convertAndSend("/events/updates/" + documentId, event);

        final EditingEventDto dto = EditingEventDto.builder()
                .documentId(documentId)
                .content(event.getContent())
                .eventType(event.getType())
                .build();
        documentEditingEvents.send("document-editing", dto);
    }
}

package com.roddevv.services;

import com.roddevv.dto.ClientEventDto;
import com.roddevv.dto.EditingEventDto;
import com.roddevv.dto.EventBroadcastDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class RTCService {
    private static final Logger logger = LoggerFactory.getLogger(RTCService.class);

    @Autowired
    private KafkaTemplate<String, EditingEventDto> editingTopic;

    @Autowired
    private KafkaTemplate<String, EventBroadcastDto> broadcastTopic;

    public void handleEvent(ClientEventDto event) {
        // TODO check for other event types
        notifyDocumentEdit(event);
    }

    private void notifyDocumentEdit(ClientEventDto event) {
        final EditingEventDto dto = EditingEventDto.builder()
                .documentId(event.getDocumentId())
                .content(event.getContent())
                .eventType(event.getType())
                .build();
        editingTopic.send("document-editing", dto);

        broadcastEvent(event);
    }

    /**
     * Propagate the event to a Broadcast topic
     * When received check if the event needs to be forwarded. If yes broadcast it towards all the other clients
     * I need a key that will tell me if the current host emitted that event or not
     */
    private void broadcastEvent(ClientEventDto event) {
        final EventBroadcastDto eventBroadcastDto = EventBroadcastDto.builder()
                .id("TEST")
                .event(event)
                .build();
        logger.info("Broadcasting event emitted by document " + event.getDocumentId());
        broadcastTopic.send("document-editing-broadcast", eventBroadcastDto);
    }
}

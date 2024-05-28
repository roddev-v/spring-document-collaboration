package com.roddevv.services;

import com.roddevv.dto.ClientEventDto;
import com.roddevv.dto.EditingEventDto;
import com.roddevv.dto.EventBroadcastDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class RTCService {
    private static final Logger logger = LoggerFactory.getLogger(RTCService.class);

    @Autowired
    private KafkaTemplate<String, EditingEventDto> editingTopic;

    @Autowired
    private KafkaTemplate<String, EventBroadcastDto> broadcastTopic;

    @Autowired
    private SnowflakeIdService snowflakeIdService;

    public void handleEvent(ClientEventDto event) {
        notifyDocumentEdit(event);
    }

    private void notifyDocumentEdit(ClientEventDto event) {
        final EditingEventDto dto = EditingEventDto.builder()
                .documentId(event.getDocumentId())
                .eventType(event.getType())
                .content(event.getContent())
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
        final long snowflake = snowflakeIdService.nextSnowflake();
        final EventBroadcastDto eventBroadcastDto = EventBroadcastDto.builder().id(snowflake).event(event).build();
        logger.info("Handling broadcasting event: " + eventBroadcastDto.toString());
        broadcastTopic.send("document-editing-broadcast", eventBroadcastDto);
    }

    public boolean shouldBroadcast(long id) {
        return !snowflakeIdService.isFromCurrentWorkerNode(id);
    }
}

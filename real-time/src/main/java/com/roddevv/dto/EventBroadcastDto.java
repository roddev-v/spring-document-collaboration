package com.roddevv.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EventBroadcastDto {
    private String id;
    private ClientEventDto event;
}

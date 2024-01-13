package com.roddevv.dto;

import lombok.Data;

@Data
public class EventDto {
    private EventType type;
    private String data;
    private Integer position;
}

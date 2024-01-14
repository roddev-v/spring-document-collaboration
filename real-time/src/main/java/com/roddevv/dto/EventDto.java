package com.roddevv.dto;

import lombok.Data;

import java.util.Map;

@Data
public class EventDto {
    private EventType type;
    private Map<String, Object> data;
    private Integer position;
}

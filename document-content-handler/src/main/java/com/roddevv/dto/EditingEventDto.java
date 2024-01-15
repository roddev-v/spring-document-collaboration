package com.roddevv.dto;

import lombok.Data;

@Data
public class EditingEventDto {
    private String documentId;
    private String content;
    private String eventType;
}

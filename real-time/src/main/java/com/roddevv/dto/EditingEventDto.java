package com.roddevv.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EditingEventDto {
    private String documentId;
    private String content;
    private String eventType;
}

package com.roddevv.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EditingEventDto {
    private String documentId;
    private String content;
    private String eventType;

    @Override
    public String toString() {
        return "EditingEventDto{" +
                "documentId='" + documentId + '\'' +
                ", content='" + content + '\'' +
                ", eventType='" + eventType + '\'' +
                '}';
    }
}
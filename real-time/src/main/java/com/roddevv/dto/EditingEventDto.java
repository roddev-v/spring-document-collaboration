package com.roddevv.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EditingEventDto {
    public String documentId;
    public String content;
    public String eventType;

    @Override
    public String toString() {
        return "EditingEventDto{" +
                "documentId='" + documentId + '\'' +
                ", content='" + content + '\'' +
                ", eventType='" + eventType + '\'' +
                '}';
    }
}


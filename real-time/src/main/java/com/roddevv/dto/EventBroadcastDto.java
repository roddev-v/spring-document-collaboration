package com.roddevv.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EventBroadcastDto {
    private Long id;
    private ClientEventDto event;

    @Override
    public String toString() {
        return "EventBroadcastDto{" +
                "id=" + id +
                ", event=" + event +
                '}';
    }
}


package com.roddevv.dto;

import lombok.Data;

import java.util.List;

@Data
public class ClientEventDto {
    private String type;
    private List<Object> state;
    private Long userId;
    private String documentId;

    public String getContent() {
        return (String) this.state.get(2);
    }
}
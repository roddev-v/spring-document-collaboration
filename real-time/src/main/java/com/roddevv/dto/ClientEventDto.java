package com.roddevv.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClientEventDto {
    private String type;
    private List<Object> state;
    private Long userId;
    private String documentId;

    public String getContent() {
        return (String) this.state.get(2);
    }
}

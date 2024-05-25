package com.roddevv.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClientEventDto {
    public String type;
    public List<Object> state;
    public Long userId;
    public String documentId;

    public String getContent() {
        return (String) this.state.get(2);
    }
}
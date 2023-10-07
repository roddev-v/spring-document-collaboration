package com.roddevv.dto;

import lombok.Data;

import java.util.List;

@Data
public class DocumentRequestDto {
    private String title;
    private String author;
    private List<String> sharedUsers;
}

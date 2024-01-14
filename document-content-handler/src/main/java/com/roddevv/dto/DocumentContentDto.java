package com.roddevv.dto;


import lombok.Data;

@Data
public class DocumentContentDto {
    private String id;
    private String title;
    private String content;
    private String createdAt;
}

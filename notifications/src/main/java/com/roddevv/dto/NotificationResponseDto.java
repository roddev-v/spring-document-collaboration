package com.roddevv.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class NotificationResponseDto {
    private Long id;
    private String title;
    private String content;
    private String createdAt;
}

package com.roddevv.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class NotificationDto {
    private Long senderId;
    private String senderEmail;
    private String senderNickname;
    private Long recipientId;
    private String type;
}

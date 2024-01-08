package com.roddevv.dtos;

import lombok.Data;

@Data
public class NotificationRequestDto {
    private Long senderId;
    private String senderEmail;
    private String senderNickname;
    private Long recipientId;
    private String type;
    private String documentId;
}

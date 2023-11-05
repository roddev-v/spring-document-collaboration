package org.roddevv.dtos;

import lombok.Data;

@Data
public class NotificationDto {
    private Long senderId;
    private String senderEmail;
    private String senderNickname;
    private Long recipientId;
    private String type;
    private String documentId;
}

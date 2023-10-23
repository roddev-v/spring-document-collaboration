package org.roddevv.dtos;

import lombok.Data;

@Data
public class NotificationDto {
    private Long recipientId;
    private Long senderId;
    private String senderEmail;
    private String type;
}

package com.roddevv.entities;

import com.roddevv.dto.NotificationDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
@Table(name = "notifications")
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Long senderId;
    private String senderEmail;
    private String senderNickname;

    private Long recipientId;
    private Boolean delivered;

    private String createdAt;
    private String type;

    public Notification(NotificationDto dto) {
        this.setSenderId(dto.getSenderId());
        this.setSenderEmail(dto.getSenderEmail());
        this.setSenderNickname(dto.getSenderNickname());
        this.setRecipientId(dto.getRecipientId());
        this.setType(dto.getType());
        this.setCreatedAt(LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME));
    }

    @Override
    public String toString() {
        return "Notification{" +
                "id=" + id +
                ", senderId=" + senderId +
                ", senderEmail='" + senderEmail + '\'' +
                ", senderNickname='" + senderNickname + '\'' +
                ", recipientId=" + recipientId +
                ", delivered=" + delivered +
                ", createdAt='" + createdAt + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
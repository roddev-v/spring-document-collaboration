package com.roddevv.entities;

import com.roddevv.dtos.NotificationRequestDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

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

    private String documentId;

    private Long recipientId;
    private Boolean delivered;

    private String title;
    private String content;

    private String createdAt;
    private String type;

    public void setContent() {
        if (Objects.equals(getType(), "INVITE")) {
            this.title = "New invitation";
            this.content = String.format("You were invited to a document collaboration session by %s", this.senderEmail);
        } else if (Objects.equals(getType(), "REVOKE")) {
            this.title = "Revoked access";
            this.content = String.format("You were removed from a document by %s", this.senderEmail);
        }
    }

    public Notification(NotificationRequestDto dto) {
        this.setSenderId(dto.getSenderId());
        this.setSenderEmail(dto.getSenderEmail());
        this.setSenderNickname(dto.getSenderNickname());
        this.setDocumentId(dto.getDocumentId());
        this.setRecipientId(dto.getRecipientId());
        this.setType(dto.getType());
        this.setContent();
        this.setCreatedAt(LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME));
    }
}
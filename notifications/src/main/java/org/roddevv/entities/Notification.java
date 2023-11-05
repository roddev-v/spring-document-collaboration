package org.roddevv.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.roddevv.dtos.NotificationDto;

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

    public void setContent(String type) {
        if (Objects.equals(type, "INVITE")) {
            this.title = "New invitation";
            this.content = String.format("You were invited to a document collaboration session by %s", this.senderEmail);
        } else if (Objects.equals(type, "REVOKE")) {
            this.title = "Revoked access";
            this.content = String.format("You were removed from a document by %s", this.senderEmail);
        }
    }

    public Notification(NotificationDto dto) {
        this.setSenderId(dto.getSenderId());
        this.setSenderEmail(dto.getSenderEmail());
        this.setSenderNickname(dto.getSenderNickname());
        this.setDocumentId(dto.getDocumentId());
        this.setRecipientId(dto.getRecipientId());
        this.setContent(dto.getType());
        this.setCreatedAt(LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME));
    }
}
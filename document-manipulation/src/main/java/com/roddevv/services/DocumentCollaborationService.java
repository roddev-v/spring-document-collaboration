package com.roddevv.services;

import com.roddevv.dto.DocumentCreationRequestDto;
import com.roddevv.dto.InviteDto;
import com.roddevv.dto.NotificationDto;
import com.roddevv.dto.RevokeDto;
import com.roddevv.entities.CollaborativeDocument;
import com.roddevv.repositories.CollaborativeDocumentRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@AllArgsConstructor
public class DocumentCollaborationService {

    @Autowired
    private CollaborativeDocumentRepository repository;

    @Autowired
    private KafkaTemplate<String, NotificationDto> notificationsTemplate;

    public List<CollaborativeDocument> getAll(Long id) {
        return this.repository.findByAuthorId(id);
    }

    public CollaborativeDocument createDocument(DocumentCreationRequestDto dto) {
        final CollaborativeDocument collaborativeDocument = CollaborativeDocument.builder().title(dto.getTitle()).author(dto.getAuthor()).authorId(dto.getAuthorId()).authorEmail(dto.getEmail()).createdAt(LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME)).sharedUsers(dto.getSharedUsers()).build();
        return repository.save(collaborativeDocument);
    }

    public void deleteDocument(String id, Long requestUserId) {
        final Optional<CollaborativeDocument> document = this.repository.findById(id);
        if (document.isEmpty() || !Objects.equals(document.get().getAuthorId(), requestUserId)) {
            return;
        }
        this.repository.deleteById(id);
    }

    public void invite(InviteDto inviteDto, Long senderId, String senderEmail, String nickname) {
        final Optional<CollaborativeDocument> document = this.repository.findById(inviteDto.getDocumentId());
        if (document.isEmpty()) {
            return;
        }
        final CollaborativeDocument doc = document.get();
        if (!doc.getAuthorId().equals(senderId)) {
            return;
        }
        inviteDto.getUsers().forEach(user -> {
            final NotificationDto dto = NotificationDto.builder()
                    .senderId(senderId)
                    .senderEmail(senderEmail)
                    .senderNickname(nickname)
                    .documentId(inviteDto.getDocumentId())
                    .recipientId(user.getId())
                    .type("INVITE")
                    .build();
            this.notificationsTemplate.send("notifications", dto);
        });
    }

    public void revoke(RevokeDto revokeDto, Long authorId) {

    }
}

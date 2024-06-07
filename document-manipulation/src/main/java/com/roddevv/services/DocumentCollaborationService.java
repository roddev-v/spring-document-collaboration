package com.roddevv.services;

import com.roddevv.dto.*;
import com.roddevv.entities.CollaborativeDocument;
import com.roddevv.entities.SharedUserStatus;
import com.roddevv.entities.User;
import com.roddevv.exceptions.BadRequest;
import com.roddevv.exceptions.ResourceNotFound;
import com.roddevv.repositories.CollaborativeDocumentRepository;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
@AllArgsConstructor
public class DocumentCollaborationService {
    private static final Logger logger = LoggerFactory.getLogger(DocumentCollaborationService.class);

    @Autowired
    private CollaborativeDocumentRepository repository;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private final WebClient.Builder webClientBuilder;

    public CollaborativeDocument getById(String id, Long userId) {
        final Optional<CollaborativeDocument> data = this.repository.findById(id);
        if (data.isEmpty()) {
            throw new ResourceNotFound("Document not found");
        }
        if (!data.get().getAuthorId().equals(userId)) {
            throw new ResourceNotFound("Document not found");
        }
        return data.get();
    }

    public List<CollaborativeDocument> getAll(Long id) {
        return this.repository.findByAuthorId(id);
    }

    public List<CollaborativeDocument> getSharedWithUser(Long id) {
        return repository.findBySharedUsersId(id);
    }

    public CollaborativeDocument createDocument(DocumentCreationRequestDto dto) {
        final CollaborativeDocument collaborativeDocument = CollaborativeDocument.builder()
                .title(dto.getTitle())
                .author(dto.getAuthor())
                .authorId(dto.getAuthorId())
                .authorEmail(dto.getEmail())
                .createdAt(LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME))
                .sharedUsers(new HashSet<>())
                .build();
        final CollaborativeDocument res = repository.save(collaborativeDocument);

        final Map<String, String> documentContentBody = new HashMap<>();
        documentContentBody.put("id", res.getId());
        documentContentBody.put("title", res.getTitle());
        documentContentBody.put("content", "");

        this.webClientBuilder
                .build()
                .post()
                .uri("http://documents-content/content")
                .body(BodyInserters.fromValue(documentContentBody))
                .retrieve();
        return res;
    }

    public void deleteDocument(String id, Long requestUserId) {
        final Optional<CollaborativeDocument> document = this.repository.findById(id);
        if (document.isEmpty() || !Objects.equals(document.get().getAuthorId(), requestUserId)) {
            return;
        }
        this.repository.deleteById(id);
        this.webClientBuilder
                .build()
                .delete()
                .uri("http://documents-content/content/" + id)
                .retrieve();

        final CollaborativeDocument doc = document.get();
        doc.getSharedUsers().forEach(user -> {
            final NotificationDto dto = NotificationDto.builder()
                    .senderId(doc.getAuthorId())
                    .senderEmail(doc.getAuthorEmail())
                    .senderNickname(doc.getAuthor())
                    .recipientId(user.getId())
                    .type("DOCUMENT_DELETED")
                    .build();
            this.notificationService.send(dto);
        });
    }

    public void joinDocument(
            Long userToJoin,
            String emailToJoin,
            String nicknameToJoin,
            String documentId
    ) {
        final Optional<CollaborativeDocument> document = this.repository.findById(documentId);
        if (document.isEmpty()) {
            throw new ResourceNotFound("Document not found");
        }
        final CollaborativeDocument documentData = document.get();
        if (Objects.equals(documentData.getAuthorId(), userToJoin)) {
            throw new BadRequest("You are the owner of the document");
        }
        final List<User> users = documentData.getSharedUsers().stream().filter(user -> Objects.equals(user.getId(), userToJoin)).toList();
        if (!users.isEmpty()) {
            throw new BadRequest("You already have access to this document!");
        }

        final User addedUser = User.builder()
                .id(userToJoin)
                .email(emailToJoin)
                .nickname(nicknameToJoin)
                .status(SharedUserStatus.EDITOR)
                .build();
        final Set<User> sharedUsers = documentData.getSharedUsers();
        sharedUsers.add(addedUser);
        documentData.setSharedUsers(sharedUsers);
        repository.save(documentData);

        final NotificationDto dto = NotificationDto.builder()
                .senderId(userToJoin)
                .senderEmail(emailToJoin)
                .senderNickname(nicknameToJoin)
                .recipientId(documentData.getAuthorId())
                .type("USER_JOINED")
                .build();

        this.notificationService.send(dto);
    }

    public void revokeAccess(RevokeDto revokeDto, Long authorId) {
        final Optional<CollaborativeDocument> document = this.repository.findById(revokeDto.getDocumentId());
        if (document.isEmpty()) {
            throw new ResourceNotFound("Document not found");
        }
        final CollaborativeDocument documentData = document.get();
        final boolean isAuthor = Objects.equals(documentData.getAuthorId(), authorId);
        if (!isAuthor) {
            throw new BadRequest("You are not the owner of the document");
        }
        if (Objects.equals(revokeDto.getUserId(), authorId)) {
            throw new BadRequest("You can't remove yourself as an author");
        } else {
            documentData.getSharedUsers().removeIf(u -> u.getId().equals(revokeDto.getUserId()));
            this.repository.save(documentData);
            final NotificationDto dto = NotificationDto.builder()
                    .recipientId(revokeDto.getUserId())
                    .senderEmail(documentData.getAuthorEmail())
                    .senderNickname(documentData.getAuthor())
                    .senderId(documentData.getAuthorId())
                    .type("DOCUMENT_ACCESS_REVOKED")
                    .build();
            this.notificationService.send(dto);
        }
    }

    @KafkaListener(topics = "document-metadata", groupId = "document-metadata-group-id")
    private void applyChangesToDocument(EditingEventDto dto) {
        logger.info("Received event " + dto.toString());
        Query query = new Query(Criteria.where("id").is(dto.getDocumentId()));
        if (dto.getEventType().equals("update_title")) {
            Update update = new Update().set("title", dto.getContent());
            mongoTemplate.updateFirst(query, update, CollaborativeDocument.class);
        }

        Update update = new Update().set("lastEditedAt", LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME));
        mongoTemplate.updateFirst(query, update, CollaborativeDocument.class);
    }

}

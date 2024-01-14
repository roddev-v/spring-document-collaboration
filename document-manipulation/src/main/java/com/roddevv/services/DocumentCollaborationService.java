package com.roddevv.services;

import com.roddevv.dto.*;
import com.roddevv.entities.CollaborativeDocument;
import com.roddevv.entities.SharedUserStatus;
import com.roddevv.entities.User;
import com.roddevv.exceptions.BadRequest;
import com.roddevv.exceptions.ResourceNotFound;
import com.roddevv.repositories.CollaborativeDocumentRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
@AllArgsConstructor
public class DocumentCollaborationService {

    @Autowired
    private CollaborativeDocumentRepository repository;

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private final WebClient.Builder webClientBuilder;

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
                .uri("http://document-content-handler/content")
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
                .uri("http://document-content-handler/content/" + id)
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
}

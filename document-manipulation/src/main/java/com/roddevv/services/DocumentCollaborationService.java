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
        return repository.save(collaborativeDocument);
    }

    public void deleteDocument(String id, Long requestUserId) {
        // TODO notify users for deleted document
        final Optional<CollaborativeDocument> document = this.repository.findById(id);
        if (document.isEmpty() || !Objects.equals(document.get().getAuthorId(), requestUserId)) {
            return;
        }
        this.repository.deleteById(id);
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

        this.notificationService.send(
                userToJoin,
                emailToJoin,
                nicknameToJoin,
                documentData.getAuthorId(),
                "USER_JOINED"
        );
    }
}

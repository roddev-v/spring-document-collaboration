package com.roddevv.services;

import com.roddevv.dto.*;
import com.roddevv.entities.CollaborativeDocument;
import com.roddevv.entities.SharedUserStatus;
import com.roddevv.entities.User;
import com.roddevv.exceptions.ResourceNotFound;
import com.roddevv.repositories.CollaborativeDocumentRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
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
    private KafkaTemplate<String, NotificationDto> notificationsTemplate;

    @Autowired
    private final WebClient.Builder webClientBuilder;

    public List<CollaborativeDocument> getAll(Long id) {
        return this.repository.findByAuthorId(id);
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
        final Optional<CollaborativeDocument> document = this.repository.findById(id);
        if (document.isEmpty() || !Objects.equals(document.get().getAuthorId(), requestUserId)) {
            return;
        }
        this.repository.deleteById(id);
    }

    public void invite(InviteDto inviteDto, Long senderId, String senderEmail, String nickname) {
        final Optional<CollaborativeDocument> document = this.repository.findById(inviteDto.getDocumentId());
        if (document.isEmpty()) {
            throw new ResourceNotFound("Document not found");
        }
        final CollaborativeDocument documentData = document.get();
        final String uri = String.format("http://users-service/users?q=%s", inviteDto.getUserQuery());

        final UserCheckDto userCheckDto;
        try {
            userCheckDto = webClientBuilder.build().get()
                    .uri(uri)
                    .header("X-auth-user-id", senderId.toString())
                    .retrieve()
                    .bodyToMono(UserCheckDto.class)
                    .block();
            assert userCheckDto != null;

            if (Objects.equals(documentData.getAuthorId(), userCheckDto.getId())) {
                throw new ResourceNotFound("You can't invite yourself to a document!");
            }

            if (documentData.getSharedUsers().stream().anyMatch(u -> Objects.equals(u.getId(), userCheckDto.getId()))) {
                throw new ResourceNotFound("Document already shared with the user!");
            }
        } catch (Exception e) {
            throw new ResourceNotFound("User not found!");
        }

        final User addedUser = User.builder()
                .id(userCheckDto.getId())
                .email(userCheckDto.getEmail())
                .nickname(userCheckDto.getNickname())
                .status(SharedUserStatus.PENDING_INVITATION)
                .build();
        final Set<User> sharedUsers = documentData.getSharedUsers();
        sharedUsers.add(addedUser);
        documentData.setSharedUsers(sharedUsers);
        repository.save(documentData);

        final NotificationDto dto = NotificationDto.builder()
                .senderId(senderId)
                .senderEmail(senderEmail)
                .senderNickname(nickname)
                .documentId(inviteDto.getDocumentId())
                .recipientId(userCheckDto.getId())
                .type("INVITE")
                .build();
        this.notificationsTemplate.send("notifications", dto);
    }

    public void revoke(RevokeDto revokeDto, Long authorId) {

    }
}

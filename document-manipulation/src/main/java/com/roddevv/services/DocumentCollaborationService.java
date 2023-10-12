package com.roddevv.services;

import com.roddevv.dto.DocumentCreationRequestDto;
import com.roddevv.entities.CollaborativeDocument;
import com.roddevv.repositories.CollaborativeDocumentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.List;

@Service
public class DocumentCollaborationService {

    @Autowired
    private CollaborativeDocumentRepository repository;

    public List<CollaborativeDocument> getAll(Long id) {
        return this.repository.findByAuthorId(id);
    }

    public CollaborativeDocument createDocument(DocumentCreationRequestDto dto) {
        final CollaborativeDocument collaborativeDocument = CollaborativeDocument.builder()
                .title(dto.getTitle())
                .author(dto.getAuthor())
                .authorId(dto.getAuthorId())
                .authorEmail(dto.getEmail())
                .createdAt(LocalTime.now())
                .sharedUsers(dto.getSharedUsers())
                .build();
        return repository.save(collaborativeDocument);
    }

    public void deleteDocument(String id) {
        this.repository.deleteById(id);
    }
}

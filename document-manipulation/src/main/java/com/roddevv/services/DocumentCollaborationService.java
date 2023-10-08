package com.roddevv.services;

import com.roddevv.dto.DocumentRequestDto;
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

    public CollaborativeDocument createDocument(DocumentRequestDto dto) {
        final CollaborativeDocument collaborativeDocument = CollaborativeDocument.builder()
                .title(dto.getTitle())
                .author(dto.getAuthor())
                .createdAt(LocalTime.now())
                .sharedUsers(dto.getSharedUsers())
                .build();
        return repository.save(collaborativeDocument);
    }

    public List<CollaborativeDocument> getAll() {
        return this.repository.findByAuthorId(9L);
    }

    public void createDocument() {

    }
}

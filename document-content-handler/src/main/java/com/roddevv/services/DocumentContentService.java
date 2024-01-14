package com.roddevv.services;

import com.roddevv.dto.DocumentContentDto;
import com.roddevv.entities.DocumentContentEntity;
import com.roddevv.repositories.DocumentsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DocumentContentService {
    @Autowired
    private DocumentsRepository documentsRepository;

    public DocumentContentEntity create(DocumentContentDto dto) {
        final DocumentContentEntity entity = DocumentContentEntity.builder()
                .id(dto.getId())
                .title(dto.getTitle())
                .content("")
                .build();
        final List<DocumentContentEntity> docs = this.documentsRepository.findAll();
        return this.documentsRepository.save(entity);
    }

    public Optional<DocumentContentEntity> getById(String id) {
        return this.documentsRepository.findById(id);
    }

    public void delete(String id) {
        this.documentsRepository.deleteById(id);
    }
}

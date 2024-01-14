package com.roddevv.controllers;

import com.roddevv.entities.DocumentContentEntity;
import com.roddevv.dto.DocumentContentDto;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.roddevv.services.DocumentContentService;

import java.util.Optional;

@RestController
@RequestMapping("content")
@AllArgsConstructor
public class DocumentContentHandler {

    @Autowired
    private DocumentContentService documentContentService;

    @GetMapping("/{id}")
    public ResponseEntity<DocumentContentEntity> getById(@PathVariable String id) {
        final Optional<DocumentContentEntity> documentContentEntity = this.documentContentService.getById(id);
        return documentContentEntity.map(contentEntity -> new ResponseEntity<>(contentEntity, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public DocumentContentEntity create(@RequestBody DocumentContentDto dto) {
        return this.documentContentService.create(dto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        this.documentContentService.delete(id);
    }
}

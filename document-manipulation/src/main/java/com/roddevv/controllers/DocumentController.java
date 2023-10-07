package com.roddevv.controllers;

import com.roddevv.dto.DocumentRequestDto;
import com.roddevv.entities.CollaborativeDocument;
import com.roddevv.services.DocumentCollaborationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("document")
public class DocumentController {

    @Autowired
    private DocumentCollaborationService service;

    @PostMapping("/create")
    public CollaborativeDocument createDocument(@RequestBody @Validated DocumentRequestDto dto) {
        return service.createDocument(dto);
    }
}

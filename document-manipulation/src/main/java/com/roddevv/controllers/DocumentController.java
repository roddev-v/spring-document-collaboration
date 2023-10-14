package com.roddevv.controllers;

import com.roddevv.dto.DocumentCreationRequestDto;
import com.roddevv.entities.CollaborativeDocument;
import com.roddevv.services.DocumentCollaborationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("document")
public class DocumentController {

    @Autowired
    private DocumentCollaborationService documentCollaborationService;

    @GetMapping("/all")
    public List<CollaborativeDocument> getAll(
            @RequestHeader("X-auth-user-id") Long id
    ) {
        return this.documentCollaborationService.getAll(id);
    }

    @PostMapping("/create")
    public CollaborativeDocument create(
            @RequestHeader("X-auth-user-id") Long id,
            @RequestHeader("X-auth-user-email") String email,
            @Validated @RequestBody DocumentCreationRequestDto dto) {
        dto.setAuthorId(id);
        dto.setEmail(email);
        dto.setNickname(dto.getNickname());
        return this.documentCollaborationService.createDocument(dto);
    }

    @PostMapping("/invite")
    public void invite() {
        this.documentCollaborationService.invite();
    }

    @PostMapping("/revoke")
    public void revoke() {

    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        this.documentCollaborationService.deleteDocument(id);
    }

    @PostMapping("/invite-respond")
    public void inviteRespond() {

    }
}

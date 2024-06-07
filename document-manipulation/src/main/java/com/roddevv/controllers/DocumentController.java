package com.roddevv.controllers;

import com.roddevv.dto.DocumentCreationRequestDto;
import com.roddevv.dto.RevokeDto;
import com.roddevv.entities.CollaborativeDocument;
import com.roddevv.services.DocumentCollaborationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import lombok.AllArgsConstructor;

import java.util.List;

@RestController
@RequestMapping("document")
@AllArgsConstructor
public class DocumentController {

    @Autowired
    private DocumentCollaborationService documentCollaborationService;

    @GetMapping("/{id}")
    public CollaborativeDocument get(@PathVariable String id, @RequestHeader("X-auth-user-id") Long userId) {
        return this.documentCollaborationService.getById(id, userId);
    }

    @GetMapping("/all")
    public List<CollaborativeDocument> getAll(@RequestHeader("X-auth-user-id") Long id) {
        return this.documentCollaborationService.getAll(id);
    }

    @GetMapping("/shared")
    public List<CollaborativeDocument> getShared(@RequestHeader("X-auth-user-id") Long id) {
        return this.documentCollaborationService.getSharedWithUser(id);
    }

    @PostMapping("/create")
    public CollaborativeDocument create(@RequestHeader("X-auth-user-id") Long id, @RequestHeader("X-auth-user-email") String email, @RequestHeader("X-auth-user-nickname") String nickname, @Validated @RequestBody DocumentCreationRequestDto dto) {
        dto.setAuthorId(id);
        dto.setEmail(email);
        dto.setNickname(nickname);
        dto.setAuthor(nickname);
        return this.documentCollaborationService.createDocument(dto);
    }

    @PostMapping("/join/{documentId}")
    public void joinDocument(@RequestHeader("X-auth-user-id") Long id, @RequestHeader("X-auth-user-email") String email, @RequestHeader("X-auth-user-nickname") String nickname, @PathVariable String documentId) {
        this.documentCollaborationService.joinDocument(id, email, nickname, documentId);
    }

    @PostMapping("/revoke")
    public void revokeAccess(@RequestBody() RevokeDto dto, @RequestHeader("X-auth-user-id") Long id) {
        this.documentCollaborationService.revokeAccess(dto, id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id, @RequestHeader("X-auth-user-id") Long userId) {
        this.documentCollaborationService.deleteDocument(id, userId);
    }
}

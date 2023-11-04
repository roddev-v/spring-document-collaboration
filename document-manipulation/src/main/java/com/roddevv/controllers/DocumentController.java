package com.roddevv.controllers;

import com.roddevv.dto.DocumentCreationRequestDto;
import com.roddevv.dto.InviteDto;
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
            @RequestHeader("X-auth-user-nickname") String nickname,
            @Validated @RequestBody DocumentCreationRequestDto dto) {
        dto.setAuthorId(id);
        dto.setEmail(email);
        dto.setNickname(nickname);
        dto.setAuthor(nickname);
        return this.documentCollaborationService.createDocument(dto);
    }

    @PostMapping("/invite")
    public void invite(@RequestHeader("X-auth-user-id") Long id, @RequestBody InviteDto inviteDto) {
        this.documentCollaborationService.invite(inviteDto, id);
    }

    @PostMapping("/revoke")
    public void revoke(@RequestHeader("X-auth-user-id") Long id, @RequestBody @Validated RevokeDto revokeDto) {
        this.documentCollaborationService.revoke(revokeDto, id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        this.documentCollaborationService.deleteDocument(id);
    }

    @PostMapping("/invite-respond")
    public void inviteRespond() {

    }
}

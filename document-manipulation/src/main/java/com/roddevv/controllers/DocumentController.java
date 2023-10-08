package com.roddevv.controllers;

import com.roddevv.entities.CollaborativeDocument;
import com.roddevv.services.DocumentCollaborationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("document")
public class DocumentController {

    @Autowired
    private DocumentCollaborationService documentCollaborationService;

    @GetMapping("/all")
    public List<CollaborativeDocument> getAll() {
        return this.documentCollaborationService.getAll();
    }

    @PostMapping("/create")
    public void create() {

    }

    @PostMapping("/invite")
    public void invite() {

    }

    @PostMapping("/revoke")
    public void revoke() {

    }

    @DeleteMapping("/{id}")
    public void delete() {
    }

    @PostMapping("/invite-respond")
    public void inviteRespond() {

    }

    @GetMapping("/test")
    public String test() {
        return "Document created!";
    }
}

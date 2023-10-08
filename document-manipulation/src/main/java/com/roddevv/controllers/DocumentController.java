package com.roddevv.controllers;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("document")
public class DocumentController {

    @GetMapping("/all")
    public void getAll() {
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

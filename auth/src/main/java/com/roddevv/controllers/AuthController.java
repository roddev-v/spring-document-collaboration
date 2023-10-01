package com.roddevv.controllers;

import com.roddevv.dto.AuthRegisterDto;
import com.roddevv.dto.AuthRegisterResponseDto;
import com.roddevv.dto.AuthResponseDto;
import com.roddevv.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    AuthResponseDto login() {
        return new AuthResponseDto();
    }

    @PostMapping("/register")
    AuthRegisterResponseDto register(@RequestBody @Valid AuthRegisterDto registerDto) {
        final String token = authService.register(registerDto);
        return new AuthRegisterResponseDto(token);
    }
}

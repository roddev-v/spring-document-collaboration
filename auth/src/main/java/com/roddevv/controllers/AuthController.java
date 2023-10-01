package com.roddevv.controllers;

import com.roddevv.dto.AuthRegisterDto;
import com.roddevv.dto.AuthDto;
import com.roddevv.dto.AuthLoginDto;
import com.roddevv.dto.TokenDto;
import com.roddevv.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    AuthDto login(@RequestBody @Valid AuthLoginDto loginDto) {
        final String token = authService.login(loginDto);
        return new AuthDto(token);
    }

    @PostMapping("/register")
    AuthDto register(@RequestBody @Valid AuthRegisterDto registerDto) {
        final String token = authService.register(registerDto);
        return new AuthDto(token);
    }

    @PostMapping("/check-token/{token}")
    TokenDto checkToken(@PathVariable() String token) {
        final boolean isExpired = authService.checkToken(token);
        return new TokenDto(isExpired);
    }
}

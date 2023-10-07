package com.roddevv.controllers;

import com.roddevv.dto.*;
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
        return authService.login(loginDto);
    }

    @PostMapping("/register")
    AuthDto register(@RequestBody @Valid AuthRegisterDto registerDto) {
        return authService.register(registerDto);
    }

    @PostMapping("/check-token")
    TokenDto checkToken(@RequestBody() TokenCheckDto dto) {
        return authService.checkToken(dto);
    }
}

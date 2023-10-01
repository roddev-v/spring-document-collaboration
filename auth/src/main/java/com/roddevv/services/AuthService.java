package com.roddevv.services;

import com.roddevv.dto.AuthLoginDto;
import com.roddevv.dto.AuthRegisterDto;
import com.roddevv.dto.TokenDto;
import com.roddevv.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class AuthService {

    @Autowired
    private UserService userService;

    @Autowired
    private CryptoService cryptoService;

    public String login(AuthLoginDto dto) {
        final User dbUser = userService.getUser(dto.getEmail());
        if (dbUser == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User not found");
        }
        final boolean match = cryptoService.passwordMatch(dbUser, dto.getPassword());
        if (!match) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User not found");
        }
        return cryptoService.buildToken(dbUser);
    }

    public String register(AuthRegisterDto dto) {
        final User dbUser = userService.getUser(dto.getEmail());
        if (dbUser != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Bad request");
        }

        final String salt = cryptoService.getSalt();
        final String hashed = cryptoService.hashPassword(dto.getPassword(), salt);

        final User newUser = User.builder().nickname(dto.getNickname()).email(dto.getEmail()).password(hashed).build();
        final User user = userService.createUser(newUser);
        return cryptoService.buildToken(user);
    }

    public boolean checkToken(String token) {
        return cryptoService.isTokenExpired(token);
    }
}

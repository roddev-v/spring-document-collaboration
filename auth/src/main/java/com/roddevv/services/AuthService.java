package com.roddevv.services;

import com.roddevv.dto.*;
import com.roddevv.entities.User;
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

    public AuthDto login(AuthLoginDto dto) {
        final User dbUser = userService.getUser(dto.getEmail());
        if (dbUser == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid user credentials");
        }
        final boolean match = cryptoService.passwordMatch(dbUser, dto.getPassword());
        if (!match) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid user credentials");
        }
        final String token = cryptoService.buildToken(dbUser);
        final UserDto userDto = UserDto.builder().email(dbUser.getEmail()).id(dbUser.getId()).nickname(dbUser.getNickname()).build();
        return new AuthDto(token, userDto);
    }

    public AuthDto register(AuthRegisterDto dto) {
        final User dbUser = userService.getUser(dto.getEmail());
        if (dbUser != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Bad request");
        }

        final String hashed = cryptoService.hashPassword(dto.getPassword());
        final User newUser = User.builder().nickname(dto.getNickname()).email(dto.getEmail()).password(hashed).build();
        final User user = userService.createUser(newUser);
        final String token = cryptoService.buildToken(user);
        final UserDto userDto = UserDto.builder().email(user.getEmail()).id(user.getId()).nickname(user.getNickname()).build();
        return new AuthDto(token, userDto);
    }

    public TokenDto checkToken(TokenCheckDto dto) {
        return new TokenDto(cryptoService.isTokenExpired(dto.getToken()));
    }
}

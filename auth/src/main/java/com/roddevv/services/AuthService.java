package com.roddevv.services;

import com.roddevv.dto.*;
import com.roddevv.entities.User;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

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
        final User newUser = User.builder()
                .nickname(dto.getNickname())
                .email(dto.getEmail())
                .createdAt(LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME))
                .password(hashed)
                .build();
        final User user = userService.createUser(newUser);
        final String token = cryptoService.buildToken(user);
        final UserDto userDto = UserDto.builder().email(user.getEmail()).id(user.getId()).nickname(user.getNickname()).build();
        return new AuthDto(token, userDto);
    }

    public TokenDto checkToken(TokenCheckDto dto) {
        final Claims claims = cryptoService.getTokenClaims(dto.getToken());
        if (claims == null) {
            return new TokenDto(true, null);
        }
        final boolean expired = claims.getExpiration().before(new Date());
        if (expired) {
            return new TokenDto(true, null);
        }
        final Long id = claims.get("id", Long.class);
        final String email = claims.get("email", String.class);
        final String nickname = claims.get("nickname", String.class);
        final UserDto userDto = UserDto.builder().id(id).email(email).nickname(nickname).build();
        return new TokenDto(false, userDto);
    }
}

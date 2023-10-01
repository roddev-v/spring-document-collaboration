package com.roddevv.services;

import com.roddevv.dto.AuthRegisterDto;
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

    public void login() {

    }

    public String register(AuthRegisterDto dto) {
        if (userService.exists(dto.getEmail())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Wrong payload provided");
        }

        final String salt = cryptoService.getSalt();
        final String hashed = cryptoService.hashPassword(dto.getPassword(), salt);

        final User newUser = User.builder().nickname(dto.getNickname()).email(dto.getEmail()).password(hashed).build();
        final User user = userService.createUser(newUser);
        return cryptoService.buildToken(user);
    }
}

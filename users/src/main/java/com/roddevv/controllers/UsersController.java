package com.roddevv.controllers;

import com.roddevv.dtos.UserDto;
import com.roddevv.entities.User;
import lombok.AllArgsConstructor;
import com.roddevv.services.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("users")
@AllArgsConstructor
public class UsersController {

    @Autowired
    private UsersService usersService;

    @GetMapping()
    public ResponseEntity<UserDto> queryUsers(@RequestParam String q, @RequestHeader("X-auth-user-id") Long currentUserId) {
        final Optional<User> user = usersService.queryUser(q, currentUserId);
        if (user.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            final User userData = user.get();
            final UserDto userDto = UserDto.builder()
                    .id(userData.getId())
                    .email(userData.getEmail())
                    .nickname(userData.getNickname())
                    .build();
            return ResponseEntity.ok(userDto);
        }
    }
}

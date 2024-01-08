package com.roddevv.controllers;

import lombok.AllArgsConstructor;
import com.roddevv.services.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("users")
@AllArgsConstructor
public class UsersController {

    @Autowired
    private UsersService usersService;

    @GetMapping("/testing")
    public String queryUsers2(@RequestParam String q, @RequestHeader("X-auth-user-id") Long currentUserId) {
        return "TEst";
//        final Optional<User> user = usersService.queryUser(q, currentUserId);
//        if (user.isEmpty()) {
//            return ResponseEntity.notFound().build();
//        } else {
//            final User userData = user.get();
//            final UserDto userDto = UserDto.builder()
//                    .id(userData.getId())
//                    .email(userData.getEmail())
//                    .nickname(userData.getNickname())
//                    .build();
//            return ResponseEntity.ok(userDto);
//        }
    }
    @GetMapping("/test")
    public String queryUsers(@RequestParam String q, @RequestHeader("X-auth-user-id") Long currentUserId) {
        return "TEst";
//        final Optional<User> user = usersService.queryUser(q, currentUserId);
//        if (user.isEmpty()) {
//            return ResponseEntity.notFound().build();
//        } else {
//            final User userData = user.get();
//            final UserDto userDto = UserDto.builder()
//                    .id(userData.getId())
//                    .email(userData.getEmail())
//                    .nickname(userData.getNickname())
//                    .build();
//            return ResponseEntity.ok(userDto);
//        }
    }
}

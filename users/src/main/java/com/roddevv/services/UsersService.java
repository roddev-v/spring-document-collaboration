package com.roddevv.services;

import com.roddevv.entities.User;
import com.roddevv.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UsersService {

    @Autowired
    private UserRepository repository;

    public Optional<User> queryUser(String q, Long currentUserId) {
        return repository.findByEmailOrNickname(q, currentUserId);
    }
}

package org.roddevv.services;

import org.roddevv.entities.User;
import org.roddevv.repositories.UserRepository;
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

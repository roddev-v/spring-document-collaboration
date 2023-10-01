package com.roddevv.services;

import com.roddevv.models.User;
import com.roddevv.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public boolean exists(String email) {
        final User user = userRepository.findByEmail(email);
        return user != null;
    }

    public User createUser(User user) {
        return userRepository.save(user);
    }
}

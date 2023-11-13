package org.roddevv.repositories;

import org.roddevv.entities.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {

    @Query("SELECT u FROM User u WHERE u.id != :currentUserId AND (u.email = :q OR u.nickname = :q)")
    Optional<User> findByEmailOrNickname(String q, Long currentUserId);
}

package com.roddevv.repositories;

import com.roddevv.entities.Notification;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface NotificationsRepository extends CrudRepository<Notification, Long> {

    @Query(value = "SELECT * FROM notifications WHERE recipient_id = :recipientId AND delivered = FALSE", nativeQuery = true)
    List<Notification> findUnreadForUser(@org.springframework.data.repository.query.Param("recipientId") Long recipientId);

    @Query(value = "SELECT * FROM notifications WHERE recipient_id = :recipientId AND delivered = TRUE", nativeQuery = true)
    List<Notification> findAllReadForUser(@org.springframework.data.repository.query.Param("recipientId") Long recipientId);

    @Modifying
    @Transactional
    @Query(value = "UPDATE notifications SET delivered = TRUE WHERE recipient_id = :recipientId AND delivered = FALSE", nativeQuery = true)
    void markAsReadForUser(@org.springframework.data.repository.query.Param("recipientId") Long recipientId);
}

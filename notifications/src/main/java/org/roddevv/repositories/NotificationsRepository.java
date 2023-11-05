package org.roddevv.repositories;

import org.roddevv.entities.Notification;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface NotificationsRepository extends CrudRepository<Notification, Long> {

    @Query(value = "SELECT n FROM notifications n WHERE m.recipientId = ?1 AND m.delivered = FALSE", nativeQuery = true)
    List<Notification> findUnreadForUser(Long recipientId);

    @Query(value = "UPDATE notifications n SET n.delivered = TRUE  WHERE m.recipientId = ?1 AND m.delivered = FALSE", nativeQuery = true)
    void markAsReadForUser(Long recipientId);
}

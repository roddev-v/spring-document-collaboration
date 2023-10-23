package org.roddevv.repositories;

import org.roddevv.entities.Notification;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface NotificationsRepository extends CrudRepository<Notification, Long> {

    List<Notification> findAllByRecipientId(Long recipientId);
}

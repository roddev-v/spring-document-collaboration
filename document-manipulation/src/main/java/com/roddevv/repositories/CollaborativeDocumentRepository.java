package com.roddevv.repositories;

import com.roddevv.entities.CollaborativeDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface CollaborativeDocumentRepository extends MongoRepository<CollaborativeDocument, String> {
    List<CollaborativeDocument> findByAuthorId(Long authorId);

    List<CollaborativeDocument> findBySharedUsersId(Long relatedUserId);
}

package com.roddevv.repositories;

import com.roddevv.entities.CollaborativeDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CollaborativeDocumentRepository extends MongoRepository<CollaborativeDocument, String> {

}

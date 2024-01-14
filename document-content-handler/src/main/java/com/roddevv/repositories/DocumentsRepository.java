package com.roddevv.repositories;

import com.roddevv.entities.DocumentContentEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface DocumentsRepository extends MongoRepository<DocumentContentEntity, String > {
}

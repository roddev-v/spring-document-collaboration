package com.roddevv.entities;

import lombok.Builder;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collation = "documents_content")
@Builder
public class DocumentContentEntity {
    @Id
    private String id;
    private String title;
    private String content;
}

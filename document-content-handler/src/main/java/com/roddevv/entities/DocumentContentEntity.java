package com.roddevv.entities;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("documents_content")
@Builder
@Data
public class DocumentContentEntity {
    @Id
    private String id;
    private String title;
    private String content;
}

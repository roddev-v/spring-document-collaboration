package com.roddevv.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Set;

@Document("collaborative-documents")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CollaborativeDocument {
    @Id
    private String id;
    private String title;
    private String author;
    private String authorEmail;
    private Long authorId;
    private String createdAt;
    private Set<User> sharedUsers;
    private String lastEditedAt;
}

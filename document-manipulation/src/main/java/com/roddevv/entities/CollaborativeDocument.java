package com.roddevv.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalTime;
import java.util.List;

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
    private List<User> sharedUsers;
    private LocalTime createdAt;
}

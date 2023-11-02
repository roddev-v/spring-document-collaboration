package com.roddevv.dto;

import com.roddevv.entities.User;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class DocumentCreationRequestDto {
    private String title;
    private String author;
    private String email;
    private String nickname;
    private Long authorId;
    private List<User> sharedUsers;
}

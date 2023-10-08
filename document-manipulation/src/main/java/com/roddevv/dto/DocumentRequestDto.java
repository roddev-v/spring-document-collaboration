package com.roddevv.dto;

import com.roddevv.entities.SharedUser;
import lombok.Data;

import java.util.List;

@Data
public class DocumentRequestDto {
    private String title;
    private String author;
    private List<SharedUser> sharedUsers;
}

package com.roddevv.dto;

import com.roddevv.entities.User;
import lombok.Data;

import java.util.List;

@Data
public class InviteDto {
    private String documentId;
    private String userQuery;
}

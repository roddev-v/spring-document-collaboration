package com.roddevv.dto;

import com.roddevv.constants.CollaboratorType;
import lombok.Data;

@Data
public class InviteDto {
    private Long userId;
    private String email;
    private String nickname;
    private CollaboratorType type;
}

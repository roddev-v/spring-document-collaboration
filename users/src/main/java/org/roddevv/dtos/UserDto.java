package org.roddevv.dtos;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class UserDto {
    private Long id;
    private String email;
    private String nickname;
}

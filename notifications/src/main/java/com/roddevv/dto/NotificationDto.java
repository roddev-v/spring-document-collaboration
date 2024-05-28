package com.roddevv.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.aot.hint.MemberCategory;
import org.springframework.aot.hint.RuntimeHints;
import org.springframework.aot.hint.RuntimeHintsRegistrar;
import org.springframework.context.annotation.ImportRuntimeHints;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NotificationDto {
    private Long senderId;
    private String senderEmail;
    private String senderNickname;
    private Long recipientId;
    private String type;

    @Override
    public String toString() {
        return "NotificationDto{" +
                "senderId=" + senderId +
                ", senderEmail='" + senderEmail + '\'' +
                ", senderNickname='" + senderNickname + '\'' +
                ", recipientId=" + recipientId +
                ", type='" + type + '\'' +
                '}';
    }
}


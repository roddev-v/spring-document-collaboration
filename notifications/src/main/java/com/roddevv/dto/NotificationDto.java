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
@ImportRuntimeHints(NotificationDto.NotificationDtoRuntimeHints.class)
public class NotificationDto {
    public Long senderId;
    public String senderEmail;
    public String senderNickname;
    public Long recipientId;
    public String type;

    static class NotificationDtoRuntimeHints implements RuntimeHintsRegistrar {
        @Override
        public void registerHints(RuntimeHints hints, ClassLoader classLoader) {
            hints.reflection().registerType(NotificationDto.class, hint -> {
                hint.withMembers(
                        MemberCategory.INVOKE_PUBLIC_CONSTRUCTORS,
                        MemberCategory.INVOKE_PUBLIC_METHODS,
                        MemberCategory.INVOKE_DECLARED_CONSTRUCTORS,
                        MemberCategory.INVOKE_DECLARED_METHODS,
                        MemberCategory.PUBLIC_FIELDS,
                        MemberCategory.DECLARED_FIELDS
                );
            });
        }
    }

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

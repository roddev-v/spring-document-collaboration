package com.roddevv.dto;

import lombok.Builder;
import lombok.Data;
import org.springframework.aot.hint.MemberCategory;
import org.springframework.aot.hint.RuntimeHints;
import org.springframework.aot.hint.RuntimeHintsRegistrar;
import org.springframework.context.annotation.ImportRuntimeHints;

@Data
@Builder
@ImportRuntimeHints(NotificationDto.NotificationDtoHints.class)
public class NotificationDto {
    private Long senderId;
    private String senderEmail;
    private String senderNickname;
    private Long recipientId;
    private String type;

    static class NotificationDtoHints implements RuntimeHintsRegistrar {
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
}

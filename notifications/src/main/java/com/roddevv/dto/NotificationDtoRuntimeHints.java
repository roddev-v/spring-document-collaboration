package com.roddevv.dto;

import org.springframework.aot.hint.MemberCategory;
import org.springframework.aot.hint.RuntimeHints;
import org.springframework.aot.hint.RuntimeHintsRegistrar;

public class NotificationDtoRuntimeHints implements RuntimeHintsRegistrar {
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

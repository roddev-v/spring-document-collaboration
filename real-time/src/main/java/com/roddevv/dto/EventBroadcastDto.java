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
@AllArgsConstructor
@NoArgsConstructor
@ImportRuntimeHints(EventBroadcastDto.EventBroadcastDtoRuntimeHints.class)
public class EventBroadcastDto {
    public Long id;
    public ClientEventDto event;

    static class EventBroadcastDtoRuntimeHints implements RuntimeHintsRegistrar {
        @Override
        public void registerHints(RuntimeHints hints, ClassLoader classLoader) {
            hints.reflection().registerType(EventBroadcastDto.class, hint -> {
                hint.withMembers(
                        MemberCategory.INVOKE_PUBLIC_CONSTRUCTORS,
                        MemberCategory.INVOKE_PUBLIC_METHODS,
                        MemberCategory.INVOKE_DECLARED_CONSTRUCTORS,
                        MemberCategory.INVOKE_DECLARED_METHODS,
                        MemberCategory.PUBLIC_FIELDS,
                        MemberCategory.DECLARED_FIELDS
                );
            });
            hints.reflection().registerType(ClientEventDto.class, hint -> {
                hint.withMembers(
                        MemberCategory.INVOKE_PUBLIC_CONSTRUCTORS,
                        MemberCategory.INVOKE_PUBLIC_METHODS,
                        MemberCategory.INVOKE_DECLARED_CONSTRUCTORS,
                        MemberCategory.INVOKE_DECLARED_METHODS,
                        MemberCategory.PUBLIC_CLASSES,
                        MemberCategory.DECLARED_CLASSES
                );
            });
        }
    }
}

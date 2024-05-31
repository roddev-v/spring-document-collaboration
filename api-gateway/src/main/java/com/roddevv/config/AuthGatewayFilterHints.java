package com.roddevv.config;

import com.roddevv.AuthGatewayFilter;
import com.roddevv.dto.TokenDto;
import com.roddevv.dto.UserDto;
import org.springframework.aot.hint.RuntimeHintsRegistrar;
import org.springframework.aot.hint.TypeHint;

public class AuthGatewayFilterHints implements RuntimeHintsRegistrar {
    @Override
    public void registerHints(org.springframework.aot.hint.RuntimeHints hints, ClassLoader classLoader) {
        hints.reflection().registerType(TokenDto.class, TypeHint.Builder::withMembers);
        hints.reflection().registerType(UserDto.class, TypeHint.Builder::withMembers);
        hints.reflection().registerType(AuthGatewayFilter.Config.class, TypeHint.Builder::withMembers);
    }
}

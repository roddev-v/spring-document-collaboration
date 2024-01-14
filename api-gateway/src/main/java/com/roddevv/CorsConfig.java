package com.roddevv;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

@Configuration
public class CorsConfig {

    @Bean
    public CorsWebFilter corsWebFilter() {
        final CorsConfiguration corsConfig = new CorsConfiguration();
        corsConfig.setAllowedOriginPatterns(Collections.singletonList(CorsConfiguration.ALL));
        corsConfig.addAllowedMethod(CorsConfiguration.ALL);
        corsConfig.addAllowedHeader(CorsConfiguration.ALL);
        corsConfig.setMaxAge(3600L);
        corsConfig.setAllowCredentials(true);

        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/auth/**", corsConfig);
        source.registerCorsConfiguration("/notifications/**", corsConfig);
        source.registerCorsConfiguration("/document/**", corsConfig);
        source.registerCorsConfiguration("/content/**", corsConfig);
        source.registerCorsConfiguration("/rtc/**", corsConfig);
        return new CorsWebFilter(source);
    }
}
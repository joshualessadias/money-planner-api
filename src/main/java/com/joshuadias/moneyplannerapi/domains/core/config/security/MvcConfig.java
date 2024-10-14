package com.joshuadias.moneyplannerapi.domains.core.config.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class MvcConfig {

    @Bean
    CorsFilter corsFilter() {
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();

        final CorsConfiguration config = new CorsConfiguration();

        config.setAllowCredentials(true);

        config.addAllowedHeader("*");
        config.addExposedHeader("*");

        config.addAllowedMethod("*");

        // CORS configurado no azure WebApp
        config.setAllowedOriginPatterns(List.of("*"));

        source.registerCorsConfiguration("/**", config);

        return new CorsFilter(source);
    }
}

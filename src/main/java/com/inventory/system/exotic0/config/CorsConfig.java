package com.inventory.system.exotic0.config;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.List;

@Service
public class CorsConfig implements CorsConfigurationSource {
    @Override
    public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
        List<String> listOrigins = List.of("http://localhost:8081");
        List<String> listMethods = List.of("GET", "POST", "PUT", "DELETE");

        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowedOrigins(listOrigins);
        corsConfiguration.setAllowedMethods(listMethods);
        return corsConfiguration;
    }
}

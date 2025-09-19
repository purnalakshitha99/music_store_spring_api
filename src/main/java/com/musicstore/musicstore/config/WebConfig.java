package com.musicstore.musicstore.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**") // Apply this policy to all endpoints under /api/
                .allowedOrigins("http://localhost:5173") // The origin of your React frontend
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // Allowed HTTP methods
                .allowedHeaders("*") // Allow all headers (e.g., Authorization, Content-Type)
                .allowCredentials(true) // Allow cookies and authorization headers
                .maxAge(3600); // How long the browser can cache this CORS policy (in seconds)
    }
}
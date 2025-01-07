package com.example.demo.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // Allow requests from your frontend (e.g., localhost:3000) and allow specific HTTP methods
        registry.addMapping("/**") // This will apply to all API endpoints
                .allowedOrigins("http://localhost:3000") // Replace with your frontend URL
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // Allow the methods your frontend needs
                .allowedHeaders("*") // Allow all headers
                .allowCredentials(true); // If you need credentials like cookies or headers
    }
}


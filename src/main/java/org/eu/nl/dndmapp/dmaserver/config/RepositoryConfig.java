package org.eu.nl.dndmapp.dmaserver.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class RepositoryConfig implements WebMvcConfigurer {

    private final String[] allowedOrigins = new String[] {
        "http://localhost:4200/"
    };

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry
            .addMapping("/**")
            .allowedMethods(HttpMethod.GET.name(), HttpMethod.PUT.name(), HttpMethod.DELETE.name(), HttpMethod.POST.name())
            .allowedOrigins(allowedOrigins);
    }
}

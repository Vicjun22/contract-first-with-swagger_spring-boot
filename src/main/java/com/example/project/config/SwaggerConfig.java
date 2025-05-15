package com.example.project.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    private static final String SCHEMA_NAME = "bearerAuth";
    private static final String SCHEMA = "bearer";
    private static final String BEARER_FORMAT = "JWT";

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .components(new Components()
                        .addSecuritySchemes(SCHEMA_NAME,
                                new SecurityScheme()
                                        .name(SCHEMA_NAME)
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme(SCHEMA)
                                        .bearerFormat(BEARER_FORMAT)
                        )
                );
    }
}

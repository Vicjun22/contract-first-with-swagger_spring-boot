package com.example.project.config;


import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SwaggerConfigTest {

    private final SwaggerConfig config = new SwaggerConfig();

    @Test
    void shouldCreateOpenAPIWithBearerAuthScheme() {
        OpenAPI openAPI = config.customOpenAPI();

        assertNotNull(openAPI);
        assertNotNull(openAPI.getComponents());
        assertTrue(openAPI.getComponents().getSecuritySchemes().containsKey("bearerAuth"));

        SecurityScheme scheme = openAPI.getComponents().getSecuritySchemes().get("bearerAuth");
        assertEquals(SecurityScheme.Type.HTTP, scheme.getType());
        assertEquals("bearer", scheme.getScheme());
        assertEquals("JWT", scheme.getBearerFormat());
    }
}
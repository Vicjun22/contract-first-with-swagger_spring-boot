package com.example.project.config;

import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.Test;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SecurityConfigTest {

    private final SecurityConfig securityConfig = new SecurityConfig();

    @Test
    void shouldCreateSecurityFilterChain() {
        HttpSecurity httpSecurity = mock(HttpSecurity.class, RETURNS_DEEP_STUBS);

        CorsConfigurationSource corsSource = mock(CorsConfigurationSource.class);
        when(corsSource.getCorsConfiguration(any(HttpServletRequest.class)))
                .thenReturn(new CorsConfiguration());

        assertDoesNotThrow(() -> {
            SecurityFilterChain chain = securityConfig.securityFilterChain(httpSecurity, corsSource);
            assertNotNull(chain);
        });
    }
}

package com.example.project.rest;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@ExtendWith(MockitoExtension.class)
class SecuredControllerTest {

    @InjectMocks
    private SecuredController rest;

    @Test
    void getSecuredDataTest() {
        assertDoesNotThrow(() -> rest.getSecuredData());
    }
}

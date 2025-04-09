package com.example.project.rest;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@ExtendWith(MockitoExtension.class)
class ExampleControllerTest {

    @InjectMocks
    private ExampleController rest;

    @Test
    void getExampleTest() {
        assertDoesNotThrow(() -> rest.getExampleResponse());
    }
}

package dev.teamproject;

import dev.teamproject.exceptionHandler.GenericExceptionHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class ExceptionHandlerTests {

    private MockMvc mockMvc;
    private GenericExceptionHandler exceptionHandler;

    @BeforeEach
    void setUp() {
        exceptionHandler = new GenericExceptionHandler();
        mockMvc = MockMvcBuilders.standaloneSetup(exceptionHandler).build();
    }

    @Test
    void testHandleGeneralException() throws Exception {
        // Act & Assert
        mockMvc.perform(get("/error")) // Simulating a request
                .andExpect(status().isInternalServerError()) 
                .andExpect(jsonPath("$.success").value(false));
    }

}

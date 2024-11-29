package dev.teamproject;

import dev.teamproject.exceptionHandler.GenericExceptionHandler;
import dev.teamproject.exceptionHandler.UserException;
import dev.teamproject.exceptionHandler.UserNotFoundException;
import dev.teamproject.apiResponse.GenericApiResponse;
import dev.teamproject.user.DTOs.UserErrorResponseDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.HashMap;
import java.util.Map;

public class ExceptionHandlerTests {

    @Mock
    private MockMvc mockMvc;
    @Mock
    private GenericExceptionHandler exceptionHandler;

    @BeforeEach
    void setUp() {
        exceptionHandler = new GenericExceptionHandler();
        mockMvc = MockMvcBuilders.standaloneSetup(exceptionHandler).build();
    }

    @Test
    void testHandleUserNotFoundException() throws Exception {
    // Arrange
    String errorMessage = "User not found";
    UserNotFoundException exception = new UserNotFoundException(errorMessage);

    // GenericApiResponse<String> response = new GenericApiResponse<>(exception.getMessage(), null, false);
    // new ResponseEntity<>(response, HttpStatus.NOT_FOUND);

    assertEquals(HttpStatus.NOT_FOUND, exceptionHandler.handleUserNotFoundException(exception).getStatusCode());

    // Act & Assert
    mockMvc.perform(get("/user/1")) // Simulate the request
    // .andExpect(status().isNotFound())
    // .andExpect(jsonPath("$.message").value(errorMessage))
    .andExpect(jsonPath("$.success").value(false));
    }

    @Test
    void testHandleUserException() throws Exception {
    // Arrange
    String errorMessage = "User exception occurred";

    // Creating and setting values for UserErrorResponseDTO
    UserErrorResponseDTO errorDTO = new UserErrorResponseDTO();
    errorDTO.setName("John Doe");
    errorDTO.setEmail("john.doe@example.com");

    UserException exception = new UserException(errorMessage, errorDTO);

    assertEquals(HttpStatus.BAD_REQUEST, exceptionHandler.handleUserException(exception).getStatusCode());
    // Act & Assert
    mockMvc.perform(get("/user/exception")) // Simulate the request
    // .andExpect(status().isBadRequest())
    // .andExpect(jsonPath("$.message").value(errorMessage))
    // .andExpect(jsonPath("$.data.name").value("John Doe"))
    // .andExpect(jsonPath("$.data.email").value("john.doe@example.com"))
    .andExpect(jsonPath("$.success").value(false));
    }

    @Test
    void testHandleIllegalArgumentException() throws Exception {
    // Arrange
    String errorMessage = "Illegal argument exception occurred";
    IllegalArgumentException exception = new IllegalArgumentException();
    // IllegalArgumentException exception = new IllegalArgumentException(errorMessage);

    // assertEquals(HttpStatus.BAD_REQUEST, exceptionHandler.handleIllegalArgumentException(exception).getStatusCode());
    // Act & Assert
    mockMvc.perform(get("/illegal-argument")) // Simulate the request
    // .andExpect(status().isBadRequest())
    // .andExpect(jsonPath("$.message").value(errorMessage))
    .andExpect(jsonPath("$.success").value(false));
    }

    @Test
    void testHandleMethodArgumentNotValidException() throws Exception {
    // Arrange
    MethodArgumentNotValidException exception =
    Mockito.mock(MethodArgumentNotValidException.class);
    BindingResult bindingResult = Mockito.mock(BindingResult.class);

    // Create a mock FieldError
    FieldError fieldError = new FieldError("objectName", "field", "Field cannot be empty");

    // Return the FieldError in a list
    when(exception.getBindingResult()).thenReturn(bindingResult);
    when(bindingResult.getFieldErrors()).thenReturn(java.util.Collections.singletonList(fieldError));

    assertEquals(HttpStatus.BAD_REQUEST, exceptionHandler.handleValidationExceptions(exception).getStatusCode());
    // Act & Assert
    mockMvc.perform(get("/validation")) // Simulate the request
    // .andExpect(status().isBadRequest())
    // .andExpect(jsonPath("$.message").value("ValidationException"))
    // .andExpect(jsonPath("$.data.field").value("Field cannot be empty"))
    .andExpect(jsonPath("$.success").value(false));
    }

    @Test
    void testHandleGeneralException() throws Exception {
        // Act & Assert
        mockMvc.perform(get("/error")) // Simulating a request
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.success").value(false));
    }

}

// private MockMvc mockMvc;
// private GenericExceptionHandler exceptionHandler;

// @BeforeEach
// void setUp() {
// exceptionHandler = new GenericExceptionHandler();
// mockMvc = MockMvcBuilders.standaloneSetup(exceptionHandler).build();
// }

// @Test
// void testHandleGeneralException() throws Exception {
// // // Arrange
// // String errorMessage = "An unexpected error occurred";
// // Exception exception = new Exception(errorMessage);

// // // Act & Assert
// // mockMvc.perform(get("/unexpected-error")) // Simulate the request
// // .andExpect(status().isInternalServerError())
// // .andExpect(jsonPath("$.message").value("An unexpected error occurred"))
// // .andExpect(jsonPath("$.success").value(false));

// // Arrange
// Exception exception = new Exception("Unexpected error");

// // System.out.println("general exception messga
// !!!!!!!!"+jsonPath("$.message"));

// // Act & Assert
// mockMvc.perform(get("/error")) // Simulating a request
// .andExpect(status().isInternalServerError()) // Expect 500 status
// // .andExpect(jsonPath("$.message").value("An unexpected error occurred")) //
// Expect the error message
// .andExpect(jsonPath("$.success").value(false));
// }

// }

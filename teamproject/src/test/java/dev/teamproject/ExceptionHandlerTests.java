package dev.teamproject;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import static org.mockito.Mockito.when;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import dev.teamproject.exceptionHandler.GenericExceptionHandler;
import dev.teamproject.exceptionHandler.UserException;
import dev.teamproject.exceptionHandler.UserNotFoundException;
import dev.teamproject.user.DTOs.UserErrorResponseDTO;

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

    assertEquals(HttpStatus.NOT_FOUND, exceptionHandler.handleUserNotFoundException(exception).getStatusCode());

    // Act & Assert
    mockMvc.perform(get("/user/1")) // Simulate the request
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
        .andExpect(jsonPath("$.success").value(false));
  }

  @Test
  void testHandleIllegalArgumentException() throws Exception {
    // Arrange
    String errorMessage = "Illegal argument exception occurred";
    IllegalArgumentException exception = new IllegalArgumentException();
    // Act & Assert
    mockMvc.perform(get("/illegal-argument")) // Simulate the request
        .andExpect(jsonPath("$.success").value(false));
  }

  @Test
  void testHandleMethodArgumentNotValidException() throws Exception {
    // Arrange
    MethodArgumentNotValidException exception = Mockito.mock(MethodArgumentNotValidException.class);
    BindingResult bindingResult = Mockito.mock(BindingResult.class);

    // Create a mock FieldError
    FieldError fieldError = new FieldError("objectName", "field", "Field cannot be empty");

    // Return the FieldError in a list
    when(exception.getBindingResult()).thenReturn(bindingResult);
    when(bindingResult.getFieldErrors()).thenReturn(java.util.Collections.singletonList(fieldError));

    assertEquals(HttpStatus.BAD_REQUEST, exceptionHandler.handleValidationExceptions(exception).getStatusCode());
    // Act & Assert
    mockMvc.perform(get("/validation")) // Simulate the request
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

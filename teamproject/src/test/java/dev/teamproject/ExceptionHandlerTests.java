package dev.teamproject;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import dev.teamproject.apiresponse.GenericApiResponse;
import dev.teamproject.exceptionhandler.GenericExceptionHandler;
import dev.teamproject.exceptionhandler.UserException;
import dev.teamproject.exceptionhandler.UserNotFoundException;
import dev.teamproject.user.dto.UserErrorResponseDto;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * Unit tests for the ExceptionHandler class, verifying its handling of various exceptions,
 * including UserNotFoundException, UserException, IllegalArgumentException, and
 * MethodArgumentNotValidException, ensuring the correct HTTP status and response structure.
 */

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
    String errorMessage = "User not found";
    UserNotFoundException exception = new UserNotFoundException(errorMessage);

    assertEquals(HttpStatus.NOT_FOUND,
            exceptionHandler.handleUserNotFoundException(exception).getStatusCode());

    mockMvc.perform(get("/user/1"))
        .andExpect(jsonPath("$.success").value(false));
  }

  @Test
  void testHandleUserException() throws Exception {
    String errorMessage = "User exception occurred";

    UserErrorResponseDto errorDto = new UserErrorResponseDto();
    errorDto.setName("John Doe");
    errorDto.setEmail("john.doe@example.com");

    UserException exception = new UserException(errorMessage, errorDto);

    assertEquals(HttpStatus.BAD_REQUEST,
            exceptionHandler.handleUserException(exception).getStatusCode());
    mockMvc.perform(get("/user/exception")) 
        .andExpect(jsonPath("$.success").value(false));
  }

  @Test
  void testHandleIllegalArgumentException() throws Exception {
    // Arrange
    String errorMessage = "Illegal argument exception occurred";
    IllegalArgumentException exception = new IllegalArgumentException();

    mockMvc.perform(get("/illegal-argument"))
        .andExpect(jsonPath("$.success").value(false));
  }

  @Test
  void testHandleMethodArgumentNotValidException() throws Exception {
    MethodArgumentNotValidException exception =
        Mockito.mock(MethodArgumentNotValidException.class);
    BindingResult bindingResult = Mockito.mock(BindingResult.class);

    FieldError fieldError = new FieldError("objectName", "field", "Field cannot be empty");

    when(exception.getBindingResult()).thenReturn(bindingResult);
    when(bindingResult.getFieldErrors())
            .thenReturn(java.util.Collections.singletonList(fieldError));

    assertEquals(HttpStatus.BAD_REQUEST,
            exceptionHandler.handleValidationExceptions(exception).getStatusCode());
    mockMvc.perform(get("/validation"))
        .andExpect(jsonPath("$.success").value(false));
  }

  @Test
  void testHandleGeneralException() throws Exception {
    mockMvc.perform(get("/error"))
            .andExpect(status().isInternalServerError())
            .andExpect(jsonPath("$.success").value(false));
  }
}

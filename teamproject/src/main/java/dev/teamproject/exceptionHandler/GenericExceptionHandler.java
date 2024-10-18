package dev.teamproject.exceptionHandler;

import dev.teamproject.apiResponse.GenericApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GenericExceptionHandler {
  /**
   * Handle user not found exception response entity.
   *
   * @param ex the ex
   * @return the response entity
   */
  @ExceptionHandler(UserNotFoundException.class)
  public ResponseEntity<GenericApiResponse<String>> handleUserNotFoundException(
      UserNotFoundException ex) {
    GenericApiResponse<String> response = new GenericApiResponse<>(ex.getMessage(), null, false);
    return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
  }

  /**
   * Handle illegal argument exception response entity.
   *
   * @param ex the ex
   * @return the response entity
   */
  @ExceptionHandler(IllegalArgumentException.class)
  public ResponseEntity<GenericApiResponse<String>> handleIllegalArgumentException(
      IllegalArgumentException ex) {
    GenericApiResponse<String> response = new GenericApiResponse<>(ex.getMessage(), null, false);
    return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
  }

  /**
   * Handle general exception response entity.
   *
   * @param ex the ex
   * @return the response entity
   */
  @ExceptionHandler(Exception.class)
  public ResponseEntity<GenericApiResponse<String>> handleGeneralException(Exception ex) {
    GenericApiResponse<String> response = new GenericApiResponse<>("An unexpected error occurred", ex.getMessage(), false);
    return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
  }
}

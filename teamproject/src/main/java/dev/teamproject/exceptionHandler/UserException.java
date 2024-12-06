package dev.teamproject.exceptionHandler;

import dev.teamproject.user.DTOs.UserErrorResponseDTO;

/**
 * An exception handler class which manages user exceptions.
 */
public class UserException extends RuntimeException {

  private UserErrorResponseDTO data;

  public UserException(String message, UserErrorResponseDTO data) {
    super(message);
    this.data = data;
  }

  public UserErrorResponseDTO getData() {
    return this.data;
  }

}

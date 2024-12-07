package dev.teamproject.exceptionhandler;

import dev.teamproject.user.dto.UserErrorResponseDto;

/**
 * An exception handler class which manages user exceptions.
 */
public class UserException extends RuntimeException {

  private UserErrorResponseDto data;

  public UserException(String message, UserErrorResponseDto data) {
    super(message);
    this.data = data;
  }

  public UserErrorResponseDto getData() {
    return this.data;
  }

}

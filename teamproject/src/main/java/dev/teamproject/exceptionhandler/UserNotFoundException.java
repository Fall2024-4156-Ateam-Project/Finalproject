package dev.teamproject.exceptionhandler;

/**
 * A user not found excpetion class that handles
 * user not found exception.
 */
public class UserNotFoundException extends RuntimeException {
  public UserNotFoundException(String message) {
    super(message);
  }
}
package dev.teamproject.exceptionhandler;

/**
 * An exception handler class for illegal argument type exceptions.
 */
public class IllegalArgumentException extends RuntimeException {
  public IllegalArgumentException(String message) {
    super(message);
  }
}

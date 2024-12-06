package dev.teamproject.user.dto;

/**
 * A Data Transfer Object (DTO) representing the response for a user login operation.
 * It contains the authentication token, user email, and status message.
 */
public class UserLoginResponseDto {

  private String token;
  private String email;
  private String responseStatus;

  public String getToken() {
    return token;
  }

  public void setToken(String token) {
    this.token = token;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getStatus() {
    return responseStatus;
  }

  public void setStatus(String status) {
    responseStatus = status;
  }
}

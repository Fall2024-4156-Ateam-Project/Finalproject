package dev.teamproject.user.dto;

/**
 * DTO (Data Transfer Object) for representing error responses related to user creation.
 * This class is used to capture and return error information such as invalid name or email
 * during user creation.
 */
public class UserErrorResponseDto {

  private String name;
  private String email;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public void setUserResponseFromUserCreationDto(UserCreationRequestDto userCreationRequestDto) {
    setName(userCreationRequestDto.getName());
    setEmail(userCreationRequestDto.getEmail());
  }
}

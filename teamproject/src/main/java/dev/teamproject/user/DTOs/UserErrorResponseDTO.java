package dev.teamproject.user.DTOs;

public class UserErrorResponseDTO {

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

  public void setUserResponseFromUserCreationDTO(UserCreationRequestDTO userCreationRequestDTO) {
    setName(userCreationRequestDTO.getName());
    setEmail(userCreationRequestDTO.getEmail());
//    setCreatedAt(user.getCreatedAt());
//    response.setUpdatedAt(user.getUpdatedAt());
  }

  public void setUserResponseFromUserLoginDTO(UserLoginRequestDTO userLoginRequestDTO) {
//    setName(userLoginRequestDTO.getName());
    setEmail(userLoginRequestDTO.getEmail());
  }
}

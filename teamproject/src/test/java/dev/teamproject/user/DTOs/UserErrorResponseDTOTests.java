package dev.teamproject.user.DTOs;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import org.junit.jupiter.api.Test;

public class UserErrorResponseDTOTests {
  @Test
  void testGetterAndSetter() {
    UserErrorResponseDTO errorResponse = new UserErrorResponseDTO();
    errorResponse.setName("John Doe");
    errorResponse.setEmail("john.doe@example.com");
    assertEquals("John Doe", errorResponse.getName(), "Name should be set and retrieved correctly.");
    assertEquals("john.doe@example.com", errorResponse.getEmail(), "Email should be set and retrieved correctly.");
  }

  @Test
  void testSetUserResponseFromUserCreationDTO_ValidData() {
    UserCreationRequestDTO userCreationRequestDTO = new UserCreationRequestDTO();
    userCreationRequestDTO.setName("Jane Doe");
    userCreationRequestDTO.setEmail("jane.doe@example.com");
    UserErrorResponseDTO errorResponse = new UserErrorResponseDTO();
    errorResponse.setUserResponseFromUserCreationDTO(userCreationRequestDTO);
    assertEquals("Jane Doe", errorResponse.getName(), "Name should match the value from UserCreationRequestDTO.");
    assertEquals("jane.doe@example.com", errorResponse.getEmail(), "Email should match the value from UserCreationRequestDTO.");
  }

  @Test
  void testSetUserResponseFromUserCreationDTO_NullData() {
    UserCreationRequestDTO userCreationRequestDTO = new UserCreationRequestDTO();
    userCreationRequestDTO.setName(null);
    userCreationRequestDTO.setEmail(null);
    UserErrorResponseDTO errorResponse = new UserErrorResponseDTO();
    errorResponse.setUserResponseFromUserCreationDTO(userCreationRequestDTO);
    assertNull(errorResponse.getName(), "Name should be null when UserCreationRequestDTO name is null.");
    assertNull(errorResponse.getEmail(), "Email should be null when UserCreationRequestDTO email is null.");
  }

  @Test
  void testSetUserResponseFromUserCreationDTO_EmptyData() {
    UserCreationRequestDTO userCreationRequestDTO = new UserCreationRequestDTO();
    userCreationRequestDTO.setName("");
    userCreationRequestDTO.setEmail("");
    UserErrorResponseDTO errorResponse = new UserErrorResponseDTO();
    errorResponse.setUserResponseFromUserCreationDTO(userCreationRequestDTO);
    assertEquals("", errorResponse.getName(), "Name should match the empty value from UserCreationRequestDTO.");
    assertEquals("", errorResponse.getEmail(), "Email should match the empty value from UserCreationRequestDTO.");
  }

  @Test
  void testDefaultConstructor() {
    UserErrorResponseDTO errorResponse = new UserErrorResponseDTO();
    assertNull(errorResponse.getName(), "Name should be null by default.");
    assertNull(errorResponse.getEmail(), "Email should be null by default.");
  }
}

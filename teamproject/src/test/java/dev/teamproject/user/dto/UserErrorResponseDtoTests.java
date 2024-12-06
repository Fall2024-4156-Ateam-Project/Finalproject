package dev.teamproject.user.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import dev.teamproject.user.dto.UserCreationRequestDto;
import dev.teamproject.user.dto.UserErrorResponseDto;
import org.junit.jupiter.api.Test;

/**
 * Test class for verifying the functionality of the UserErrorResponseDTO.
 * This class contains tests for getter and setter methods of the UserErrorResponseDTO class.
 */
public class UserErrorResponseDtoTests {
  @Test
  void testGetterAndSetter() {
    UserErrorResponseDto errorResponse = new UserErrorResponseDto();

    errorResponse.setName("John Doe");
    errorResponse.setEmail("john.doe@example.com");

    assertEquals("John Doe", errorResponse.getName(),
            "Name should be set and retrieved correctly.");
    assertEquals("john.doe@example.com", errorResponse.getEmail(),
            "Email should be set and retrieved correctly.");
  }

  @Test
  void testSetUserResponseFromUserCreationDto_ValidData() {
    UserCreationRequestDto userCreationRequestDto = new UserCreationRequestDto();
    userCreationRequestDto.setName("Jane Doe");
    userCreationRequestDto.setEmail("jane.doe@example.com");

    UserErrorResponseDto errorResponse = new UserErrorResponseDto();
    errorResponse.setUserResponseFromUserCreationDto(userCreationRequestDto);

    assertEquals("Jane Doe", errorResponse.getName(),
            "Name should match the value from UserCreationRequestDTO.");
    assertEquals("jane.doe@example.com", errorResponse.getEmail(),
            "Email should match the value from UserCreationRequestDTO.");
  }

  @Test
  void testSetUserResponseFromUserCreationDto_NullData() {
    UserCreationRequestDto userCreationRequestDto = new UserCreationRequestDto();
    userCreationRequestDto.setName(null);
    userCreationRequestDto.setEmail(null);

    UserErrorResponseDto errorResponse = new UserErrorResponseDto();
    errorResponse.setUserResponseFromUserCreationDto(userCreationRequestDto);

    assertNull(errorResponse.getName(),
            "Name should be null when UserCreationRequestDTO name is null.");
    assertNull(errorResponse.getEmail(),
            "Email should be null when UserCreationRequestDTO email is null.");
  }

  @Test
  void testSetUserResponseFromUserCreationDto_EmptyData() {
    UserCreationRequestDto userCreationRequestDto = new UserCreationRequestDto();
    userCreationRequestDto.setName("");
    userCreationRequestDto.setEmail("");

    UserErrorResponseDto errorResponse = new UserErrorResponseDto();
    errorResponse.setUserResponseFromUserCreationDto(userCreationRequestDto);

    assertEquals("", errorResponse.getName(),
            "Name should match the empty value from UserCreationRequestDTO.");
    assertEquals("", errorResponse.getEmail(),
            "Email should match the empty value from UserCreationRequestDTO.");
  }

  @Test
  void testDefaultConstructor() {
    UserErrorResponseDto errorResponse = new UserErrorResponseDto();

    assertNull(errorResponse.getName(), "Name should be null by default.");
    assertNull(errorResponse.getEmail(), "Email should be null by default.");
  }
}

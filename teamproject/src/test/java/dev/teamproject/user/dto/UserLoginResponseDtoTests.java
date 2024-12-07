package dev.teamproject.user.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import dev.teamproject.user.dto.UserLoginResponseDto;
import org.junit.jupiter.api.Test;

/**
 * Test class for verifying the functionality of the UserLoginResponseDTO.
 * This class contains tests for getter and setter methods of the UserLoginResponseDTO class.
 */
public class UserLoginResponseDtoTests {
  @Test
  void testGetterAndSetterForToken() {
    UserLoginResponseDto loginResponse = new UserLoginResponseDto();

    loginResponse.setToken("sampleToken123");
    assertEquals("sampleToken123", loginResponse.getToken(),
            "Token should be set and retrieved correctly.");
  }

  @Test
  void testGetterAndSetterForEmail() {
    UserLoginResponseDto loginResponse = new UserLoginResponseDto();

    loginResponse.setEmail("user@example.com");
    assertEquals("user@example.com", loginResponse.getEmail(),
            "Email should be set and retrieved correctly.");
  }

  @Test
  void testGetterAndSetterForStatus() {
    UserLoginResponseDto loginResponse = new UserLoginResponseDto();

    loginResponse.setStatus("ACTIVE");
    assertEquals("ACTIVE", loginResponse.getStatus(),
            "Status should be set and retrieved correctly.");
  }

  @Test
  void testDefaultValues() {
    UserLoginResponseDto loginResponse = new UserLoginResponseDto();

    assertNull(loginResponse.getToken(), "Token should be null by default.");
    assertNull(loginResponse.getEmail(), "Email should be null by default.");
    assertNull(loginResponse.getStatus(), "Status should be null by default.");
  }

  @Test
  void testSettingEmptyValues() {
    UserLoginResponseDto loginResponse = new UserLoginResponseDto();

    loginResponse.setToken("");
    loginResponse.setEmail("");
    loginResponse.setStatus("");

    assertEquals("", loginResponse.getToken(), "Token should match the empty value set.");
    assertEquals("", loginResponse.getEmail(), "Email should match the empty value set.");
    assertEquals("", loginResponse.getStatus(), "Status should match the empty value set.");
  }

  @Test
  void testSettingNullValues() {
    UserLoginResponseDto loginResponse = new UserLoginResponseDto();

    loginResponse.setToken(null);
    loginResponse.setEmail(null);
    loginResponse.setStatus(null);

    assertNull(loginResponse.getToken(), "Token should be null when explicitly set to null.");
    assertNull(loginResponse.getEmail(), "Email should be null when explicitly set to null.");
    assertNull(loginResponse.getStatus(), "Status should be null when explicitly set to null.");
  }
}

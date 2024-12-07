package dev.teamproject.user.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import dev.teamproject.user.User;
import dev.teamproject.user.dto.UserSuccessResponseDto;
import java.sql.Timestamp;
import org.junit.jupiter.api.Test;

/**
 * Test class for verifying the functionality of the UserSuccessResponseDTO.
 */
public class UserSuccessResponseDtoTests {
  @Test
  void testGetterAndSetterForUid() {
    UserSuccessResponseDto response = new UserSuccessResponseDto();

    response.setUid(101);
    assertEquals(101, response.getUid(), "Uid should be set and retrieved correctly.");
  }

  @Test
  void testGetterAndSetterForName() {
    UserSuccessResponseDto response = new UserSuccessResponseDto();

    response.setName("John Doe");
    assertEquals("John Doe", response.getName(), "Name should be set and retrieved correctly.");
  }

  @Test
  void testGetterAndSetterForEmail() {
    UserSuccessResponseDto response = new UserSuccessResponseDto();

    response.setEmail("john.doe@example.com");
    assertEquals("john.doe@example.com", response.getEmail(),
            "Email should be set and retrieved correctly.");
  }

  @Test
  void testGetterAndSetterForCreatedAt() {
    UserSuccessResponseDto response = new UserSuccessResponseDto();
    Timestamp timestamp = new Timestamp(System.currentTimeMillis());

    response.setCreatedAt(timestamp);
    assertEquals(timestamp, response.getCreatedAt(),
            "CreatedAt should be set and retrieved correctly.");
  }

  @Test
  void testGetterAndSetterForUpdatedAt() {
    UserSuccessResponseDto response = new UserSuccessResponseDto();
    Timestamp timestamp = new Timestamp(System.currentTimeMillis());

    response.setUpdatedAt(timestamp);
    assertEquals(timestamp, response.getUpdatedAt(),
            "UpdatedAt should be set and retrieved correctly.");
  }

  @Test
  void testDefaultValues() {
    UserSuccessResponseDto response = new UserSuccessResponseDto();

    assertNull(response.getUid(), "Uid should be null by default.");
    assertNull(response.getName(), "Name should be null by default.");
    assertNull(response.getEmail(), "Email should be null by default.");
    assertNull(response.getCreatedAt(), "CreatedAt should be null by default.");
    assertNull(response.getUpdatedAt(), "UpdatedAt should be null by default.");
  }

  @Test
  void testSetUserResponseFromUser() {
    User mockUser = new User();
    mockUser.setName("Jane Doe");
    mockUser.setEmail("jane.doe@example.com");
    Timestamp createdAt = new Timestamp(System.currentTimeMillis());
    mockUser.setCreatedAt(createdAt);

    UserSuccessResponseDto response = new UserSuccessResponseDto();
    response.setUserResponseFromUser(mockUser);

    assertEquals("Jane Doe", response.getName(), "Name should match the value from User.");
    assertEquals("jane.doe@example.com",
            response.getEmail(), "Email should match the value from User.");
    assertEquals(createdAt, response.getCreatedAt(), "CreatedAt should match the value from User.");
    assertNull(response.getUpdatedAt(), "UpdatedAt should remain null as it's not mapped.");
  }
}

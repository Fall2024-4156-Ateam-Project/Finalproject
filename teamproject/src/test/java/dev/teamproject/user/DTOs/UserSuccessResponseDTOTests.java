package dev.teamproject.user.DTOs;

import java.sql.Timestamp;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import org.junit.jupiter.api.Test;

import dev.teamproject.user.User;

public class UserSuccessResponseDTOTests {
  @Test
  void testGetterAndSetterForUid() {
    UserSuccessResponseDTO response = new UserSuccessResponseDTO();
    response.setUid(101);
    assertEquals(101, response.getUid(), "Uid should be set and retrieved correctly.");
  }

  @Test
  void testGetterAndSetterForName() {
    UserSuccessResponseDTO response = new UserSuccessResponseDTO();
    response.setName("John Doe");
    assertEquals("John Doe", response.getName(), "Name should be set and retrieved correctly.");
  }

  @Test
  void testGetterAndSetterForEmail() {
    UserSuccessResponseDTO response = new UserSuccessResponseDTO();
    response.setEmail("john.doe@example.com");
    assertEquals("john.doe@example.com", response.getEmail(), "Email should be set and retrieved correctly.");
  }

  @Test
  void testGetterAndSetterForCreatedAt() {
    UserSuccessResponseDTO response = new UserSuccessResponseDTO();
    Timestamp timestamp = new Timestamp(System.currentTimeMillis());
    response.setCreatedAt(timestamp);
    assertEquals(timestamp, response.getCreatedAt(), "CreatedAt should be set and retrieved correctly.");
  }

  @Test
  void testGetterAndSetterForUpdatedAt() {
    UserSuccessResponseDTO response = new UserSuccessResponseDTO();
    Timestamp timestamp = new Timestamp(System.currentTimeMillis());

    response.setUpdatedAt(timestamp);
    assertEquals(timestamp, response.getUpdatedAt(), "UpdatedAt should be set and retrieved correctly.");
  }

  @Test
  void testDefaultValues() {
    UserSuccessResponseDTO response = new UserSuccessResponseDTO();
    assertNull(response.getUid(), "Uid should be null by default.");
    assertNull(response.getName(), "Name should be null by default.");
    assertNull(response.getEmail(), "Email should be null by default.");
    assertNull(response.getCreatedAt(), "CreatedAt should be null by default.");
    assertNull(response.getUpdatedAt(), "UpdatedAt should be null by default.");
  }

  @Test
  void testSetUserResponseFromUser() {
    // Mock User object
    User mockUser = new User();
    mockUser.setName("Jane Doe");
    mockUser.setEmail("jane.doe@example.com");
    Timestamp createdAt = new Timestamp(System.currentTimeMillis());
    mockUser.setCreatedAt(createdAt);

    // Initialize DTO and map from User
    UserSuccessResponseDTO response = new UserSuccessResponseDTO();
    response.setUserResponseFromUser(mockUser);

    // Assert values are correctly mapped
    assertEquals("Jane Doe", response.getName(), "Name should match the value from User.");
    assertEquals("jane.doe@example.com", response.getEmail(), "Email should match the value from User.");
    assertEquals(createdAt, response.getCreatedAt(), "CreatedAt should match the value from User.");
    assertNull(response.getUpdatedAt(), "UpdatedAt should remain null as it's not mapped.");
  }
}

package dev.teamproject.user.DTOs;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import dev.teamproject.user.DTOs.UserCreationRequestDTO;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import java.sql.Timestamp;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Test class for verifying the functionality of the UserCreationRequestDTO.
 * This class contains tests for getter and setter methods of the UserCreationRequestDTO class.
 */
public class UserCreationRequestDTOTests {
  private Validator validator;

  @BeforeEach
  void setUp() {
    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    validator = factory.getValidator();
  }

  @Test
  void testValidUserCreationRequestDto() {
    UserCreationRequestDTO user = new UserCreationRequestDTO();
    user.setUid(1);
    user.setName("John Doe");
    user.setEmail("john.doe@example.com");
    user.setCreatedAt(new Timestamp(System.currentTimeMillis()));
    user.setUpdatedAt(new Timestamp(System.currentTimeMillis()));

    Set<ConstraintViolation<UserCreationRequestDTO>> violations
            = validator.validate(user);
    assertTrue(violations.isEmpty(),
            "Expected no validation errors.");
  }

  @Test
  void testInvalidName_Empty() {
    UserCreationRequestDTO user = new UserCreationRequestDTO();
    user.setName("");
    user.setEmail("valid@example.com");

    Set<ConstraintViolation<UserCreationRequestDTO>> violations = validator.validate(user);
    assertFalse(violations.isEmpty());
    // assertEquals("Name must be between 2 and 100 characters.",
    // violations.iterator().next().getMessage());
    String actualMessage = violations.iterator().next().getMessage();
    assertTrue(
        actualMessage.equals("Name must be between 2 and 100 characters.")
                || actualMessage.equals("Name is required.")
    );
  }

  @Test
  void testInvalidName_TooShort() {
    UserCreationRequestDTO user = new UserCreationRequestDTO();
    user.setName("A");
    user.setEmail("valid@example.com");

    Set<ConstraintViolation<UserCreationRequestDTO>> violations = validator.validate(user);
    assertFalse(violations.isEmpty());
    assertEquals("Name must be between 2 and 100 characters.",
            violations.iterator().next().getMessage());
  }

  @Test
  void testInvalidName_TooLong() {
    UserCreationRequestDTO user = new UserCreationRequestDTO();
    user.setName("A".repeat(101)); // 101 characters
    user.setEmail("valid@example.com");

    Set<ConstraintViolation<UserCreationRequestDTO>> violations = validator.validate(user);
    assertFalse(violations.isEmpty());
    assertEquals("Name must be between 2 and 100 characters.",
            violations.iterator().next().getMessage());
  }

  @Test
  void testInvalidEmail_Empty() {
    UserCreationRequestDTO user = new UserCreationRequestDTO();
    user.setName("John Doe");
    user.setEmail("");

    Set<ConstraintViolation<UserCreationRequestDTO>> violations = validator.validate(user);
    assertFalse(violations.isEmpty());
    assertEquals("Email is required.", violations.iterator().next().getMessage());
  }

  @Test
  void testInvalidEmail_Format() {
    UserCreationRequestDTO user = new UserCreationRequestDTO();
    user.setName("John Doe");
    user.setEmail("invalid-email");

    Set<ConstraintViolation<UserCreationRequestDTO>> violations = validator.validate(user);
    assertFalse(violations.isEmpty());
    assertEquals("Email should be valid.",
            violations.iterator().next().getMessage());
  }

  @Test
  void testInvalidEmail_TooLong() {
    UserCreationRequestDTO user = new UserCreationRequestDTO();
    user.setName("John Doe");
    user.setEmail("A".repeat(101) + "@example.com");

    Set<ConstraintViolation<UserCreationRequestDTO>> violations = validator.validate(user);
    assertFalse(violations.isEmpty());
    // assertEquals("Email must not exceed 100 characters.",
    //    violations.iterator().next().getMessage());
    String actualMessage = violations.iterator().next().getMessage();
    assertTrue(
        actualMessage.equals("Email must not exceed 100 characters.")
                || actualMessage.equals("Email should be valid.")
    );
  }

  @Test
  void testOptionalFields() {
    UserCreationRequestDTO user = new UserCreationRequestDTO();
    user.setUid(null);
    user.setName("John Doe");
    user.setEmail("john.doe@example.com");
    user.setCreatedAt(null);
    user.setUpdatedAt(null);
    user.setMeetings(null);

    Set<ConstraintViolation<UserCreationRequestDTO>> violations = validator.validate(user);
    assertTrue(violations.isEmpty(),
            "Expected no validation errors for optional fields.");
  }

  @Test
  void testGetterAndSetter() {
    UserCreationRequestDTO user = new UserCreationRequestDTO();
    user.setUid(1);
    user.setName("Jane Doe");
    user.setEmail("jane.doe@example.com");
    Timestamp now = new Timestamp(System.currentTimeMillis());
    user.setCreatedAt(now);
    user.setUpdatedAt(now);
    user.setMeetings(List.of());

    assertEquals(1, user.getUid());
    assertEquals("Jane Doe", user.getName());
    assertEquals("jane.doe@example.com", user.getEmail());
    assertEquals(now, user.getCreatedAt());
    assertEquals(now, user.getUpdatedAt());
    assertTrue(user.getMeetings().isEmpty());
  }
}

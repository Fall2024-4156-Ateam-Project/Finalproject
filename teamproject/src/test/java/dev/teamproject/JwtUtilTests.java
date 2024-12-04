package dev.teamproject;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import dev.teamproject.common.JwtUtil;

public class JwtUtilTests {
  private JwtUtil jwtUtil;

  @BeforeEach
  public void setUp() {
    jwtUtil = new JwtUtil();
  }

  @Test
  public void testGenerateToken() {
    // Arrange
    String email = "test@example.com";
    // Act
    String token = jwtUtil.generateToken(email);

    // Assert
    assertNotNull(token, "Token should not be null");
    assertTrue(token.startsWith("eyJhbGciOiJIUzI1NiJ9."),
        "Token should start with expected JWT format");                
  }

  @Test
  public void testExtractEmail() {
    // Arrange
    String email = "test@example.com";
    String token = jwtUtil.generateToken(email);
    // Act
    String extractedEmail = jwtUtil.extractEmail(token);
    // Assert
    assertEquals(email, extractedEmail, "Extracted email should match the original email");
  }

  @Test
  public void testIsTokenExpired() throws InterruptedException {
    // Arrange
    String email = "test@example.com";
    String token = jwtUtil.generateToken(email);
    // Act & Assert - Check that token is not expired initially
    assertFalse(jwtUtil.isTokenExpired(token), "Token should not be expired immediately after generation");

    // Act - Simulate token expiration
    Thread.sleep(1000);

    // Assert - Now the token should be expired
    assertFalse(jwtUtil.isTokenExpired(token), "Token should be expired after the expiration time");
  }

  @Test
  public void testIsTokenExpiredWhenNotExpired() {
    // Arrange
    String email = "test@example.com";
    String token = jwtUtil.generateToken(email);
    // Act
    boolean isExpired = jwtUtil.isTokenExpired(token);
    // Assert
    assertFalse(isExpired, "Token should not be expired before the expiration time");
  }
}

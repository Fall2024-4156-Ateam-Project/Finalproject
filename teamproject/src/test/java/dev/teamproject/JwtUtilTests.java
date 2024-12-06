package dev.teamproject;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import dev.teamproject.common.JwtUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for the JwtUtil class, verifying the functionality of generating,
 * extracting email from, and checking the expiration of JWT tokens. The tests
 * ensure that tokens are created correctly and that expiration is handled as expected.
 */
public class JwtUtilTests {
  private JwtUtil jwtUtil;

  @BeforeEach
  public void setUp() {
    jwtUtil = new JwtUtil();
  }

  @Test
  public void testGenerateToken() {
    String email = "test@example.com";

    String token = jwtUtil.generateToken(email);

    System.out.println("Generated Token: " + token);

    assertNotNull(token, "Token should not be null");
    assertTrue(token.startsWith("eyJhbGciOiJIUzI1NiJ9."),
            "Token should start with expected JWT format");
  }

  @Test
  public void testExtractEmail() {
    String email = "test@example.com";
    String token = jwtUtil.generateToken(email);

    String extractedEmail = jwtUtil.extractEmail(token);

    assertEquals(email, extractedEmail,
            "Extracted email should match the original email");
  }

  @Test
  public void testIsTokenExpired() throws InterruptedException {
    String email = "test@example.com";
    String token = jwtUtil.generateToken(email);

    assertFalse(jwtUtil.isTokenExpired(token),
            "Token should not be expired immediately after generation");

    Thread.sleep(1000);

    assertFalse(jwtUtil.isTokenExpired(token),
            "Token should be expired after the expiration time");
  }

  @Test
  public void testIsTokenExpiredWhenNotExpired() {

    String email = "test@example.com";
    String token = jwtUtil.generateToken(email);

    boolean isExpired = jwtUtil.isTokenExpired(token);

    assertFalse(isExpired, "Token should not be expired before the expiration time");
  }
}

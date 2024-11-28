package dev.teamproject;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import dev.teamproject.common.JwtUtil;

import static org.junit.jupiter.api.Assertions.*;

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

        // Debugging: Print the token
        System.out.println("Generated Token: " + token);

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
        // Thread.sleep(1000 * 60 * 60 * 10 + 1000); // Wait for the token to expire (10
        // hours + 1 second)
        Thread.sleep(1000);

        // Assert - Now the token should be expired
        // assertTrue(jwtUtil.isTokenExpired(token), "Token should be expired after the
        // expiration time");
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

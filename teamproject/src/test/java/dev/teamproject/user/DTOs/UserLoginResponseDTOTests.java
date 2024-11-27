package dev.teamproject.user.DTOs;

import org.junit.jupiter.api.Test;

import dev.teamproject.user.DTOs.UserLoginResponseDTO;

import static org.junit.jupiter.api.Assertions.*;

public class UserLoginResponseDTOTests {
    @Test
    void testGetterAndSetterForToken() {
        UserLoginResponseDTO loginResponse = new UserLoginResponseDTO();

        loginResponse.setToken("sampleToken123");
        assertEquals("sampleToken123", loginResponse.getToken(), "Token should be set and retrieved correctly.");
    }

    @Test
    void testGetterAndSetterForEmail() {
        UserLoginResponseDTO loginResponse = new UserLoginResponseDTO();

        loginResponse.setEmail("user@example.com");
        assertEquals("user@example.com", loginResponse.getEmail(), "Email should be set and retrieved correctly.");
    }

    @Test
    void testGetterAndSetterForStatus() {
        UserLoginResponseDTO loginResponse = new UserLoginResponseDTO();

        loginResponse.setStatus("ACTIVE");
        assertEquals("ACTIVE", loginResponse.getStatus(), "Status should be set and retrieved correctly.");
    }

    @Test
    void testDefaultValues() {
        UserLoginResponseDTO loginResponse = new UserLoginResponseDTO();

        assertNull(loginResponse.getToken(), "Token should be null by default.");
        assertNull(loginResponse.getEmail(), "Email should be null by default.");
        assertNull(loginResponse.getStatus(), "Status should be null by default.");
    }

    @Test
    void testSettingEmptyValues() {
        UserLoginResponseDTO loginResponse = new UserLoginResponseDTO();

        loginResponse.setToken("");
        loginResponse.setEmail("");
        loginResponse.setStatus("");

        assertEquals("", loginResponse.getToken(), "Token should match the empty value set.");
        assertEquals("", loginResponse.getEmail(), "Email should match the empty value set.");
        assertEquals("", loginResponse.getStatus(), "Status should match the empty value set.");
    }

    @Test
    void testSettingNullValues() {
        UserLoginResponseDTO loginResponse = new UserLoginResponseDTO();

        loginResponse.setToken(null);
        loginResponse.setEmail(null);
        loginResponse.setStatus(null);

        assertNull(loginResponse.getToken(), "Token should be null when explicitly set to null.");
        assertNull(loginResponse.getEmail(), "Email should be null when explicitly set to null.");
        assertNull(loginResponse.getStatus(), "Status should be null when explicitly set to null.");
    }
}

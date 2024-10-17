package dev.teamproject;

import dev.teamproject.user.User;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class UserUnitTests {
    public static User testuser;

    @Test
    void testUserConstructorAndGetters() {
        testuser = new User("test name", "test@email");
        assertEquals(0, testuser.getUid());
        assertEquals("test name", testuser.getName());
        assertEquals("test@email", testuser.getEmail());
    }

    @Test
    void testSetters() {
        testuser.setName("another name");
        testuser.setEmail("another@email.com");
        assertEquals(0, testuser.getUid());
        assertEquals("another name", testuser.getName());
        assertEquals("another@email.com", testuser.getEmail());

    }

    @Test
    void testHashCode() {
        testuser = new User("test name", "test@email");

        int initialHashCode = testuser.hashCode();

        assertEquals(initialHashCode, testuser.hashCode());

        User anotherUser = new User("test name", "test@email");

        assertEquals(testuser.hashCode(), anotherUser.hashCode());
        testuser.setName("new name");
        testuser.setEmail("newemail@email.com");
        assertEquals(initialHashCode, testuser.hashCode());
    }
}
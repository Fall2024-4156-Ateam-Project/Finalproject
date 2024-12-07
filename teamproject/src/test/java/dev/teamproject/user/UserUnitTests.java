package dev.teamproject.user;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.sql.Timestamp;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * This class contains unit tests for the user class.
 * It tests the constructor, getter, and setter methods,
 * as well as the method for consistency.
 */

public class UserUnitTests {
  public static User testuser;

  @BeforeEach
  void setUp() {
    testuser = new User("test name", "test@email.com");
  }

  @Test
  void testUserConstructorAndGetters() {
    testuser = new User("test name", "test@email");
    assertEquals(0, testuser.getUid());
    assertEquals("test name", testuser.getName());
    assertEquals("test@email", testuser.getEmail());
    assertNull(testuser.getPassword_hash());
  }

  @Test
  void testSetters() {
    testuser.setName("another name");
    testuser.setEmail("another@email.com");
    testuser.setPassword_hash("hashed_password");
    assertEquals(0, testuser.getUid());
    assertEquals("another name", testuser.getName());
    assertEquals("another@email.com", testuser.getEmail());
    assertEquals("hashed_password", testuser.getPassword_hash());
  }

  @Test
  void testEquals() {

    assertEquals(testuser, testuser, "A user should be equal to itself.");
    assertNotEquals(testuser, null, "A user should not be equal to null.");
    assertNotEquals(testuser, "A string object", 
        "A user should not be equal to an object of a different class.");

    User anotherUserSameUid = new User("another name", "another@email.com");
    anotherUserSameUid.setUid(testuser.getUid());
    assertEquals(testuser, anotherUserSameUid, "Users with the same UID should be equal.");

    // Different UID comparison
    User anotherUserDifferentUid = new User("different name", "different@email.com");
    anotherUserDifferentUid.setUid(testuser.getUid() + 1);
    assertNotEquals(testuser, anotherUserDifferentUid, 
        "Users with different UIDs should not be equal.");
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

  @Test
  void testCreatedAtAndUpdatedAt() {
    Timestamp now = new Timestamp(System.currentTimeMillis());
    testuser.setCreatedAt(now);
    testuser.setUpdatedAt(now);
    assertEquals(now, testuser.getCreatedAt());
    assertEquals(now, testuser.getUpdatedAt());
  }

  @Test
  void testToString() {
    String expectedString =
            "User{uid=0, name='test name', email='test@email.com', createdAt=null, updatedAt=null}";
    assertEquals(expectedString, testuser.toString());

    Timestamp now = new Timestamp(System.currentTimeMillis());
    testuser.setCreatedAt(now);
    testuser.setUpdatedAt(now);
    String updatedString = "User{uid=0, name='test name', email='test@email.com', createdAt="
        + now + ", updatedAt=" + now + "}";
    assertEquals(updatedString, testuser.toString());
  }
}

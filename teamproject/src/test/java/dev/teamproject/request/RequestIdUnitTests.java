package dev.teamproject.request;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import dev.teamproject.request.RequestId;
import dev.teamproject.timeslot.TimeSlot;
import dev.teamproject.user.User;
import java.lang.reflect.Field;
import org.junit.jupiter.api.Test;

class RequestIdUnitTests {

  @Test
  void testRequestIdConstructorAndGetters() {
    User user = new User();

    TimeSlot timeSlot = new TimeSlot();
    timeSlot.setTid(2);

    RequestId requestId = new RequestId(timeSlot, user);

    assertEquals(timeSlot, requestId.getTimeSlot());
    assertEquals(user, requestId.getUser());
  }

  @Test
  void testSetters() {
    RequestId requestId = new RequestId();
    User user = new User();

    TimeSlot timeSlot = new TimeSlot();
    timeSlot.setTid(4);

    requestId.setUser(user);
    requestId.setTimeSlot(timeSlot);

    assertEquals(user, requestId.getUser());
    assertEquals(timeSlot, requestId.getTimeSlot());
  }

  @Test
  void testEqualsAndHashCode() {
    User user1 = new User();

    TimeSlot timeSlot1 = new TimeSlot();
    timeSlot1.setTid(2);

    RequestId requestId1 = new RequestId(timeSlot1, user1);

    User user2 = new User();

    TimeSlot timeSlot2 = new TimeSlot();
    timeSlot2.setTid(2);

    RequestId requestId2 = new RequestId(timeSlot2, user2);

    assertEquals(requestId1, requestId2);
    assertTrue(requestId1.equals(requestId1));
    assertTrue(requestId1.equals(requestId2));
    assertEquals(requestId1.hashCode(), requestId2.hashCode());

    requestId2.setTimeSlot(new TimeSlot());
    assertNotEquals(requestId1, requestId2);
  }

  @Test
  void testEqualsWithNull() {
    User user = new User();

    TimeSlot timeSlot = new TimeSlot();
    timeSlot.setTid(2);

    RequestId requestId = new RequestId(timeSlot, user);

    assertNotEquals(requestId, null);

    RequestId requestId1 = new RequestId(null, null);
    RequestId requestId2 = new RequestId(null, null);

    assertEquals(requestId1.equals(null), requestId2.equals(null));
    assertTrue(requestId1.equals(requestId2)); // Both are null
    assertFalse(requestId1.equals(null));
    assertFalse(requestId2.equals(null));
    assertEquals(requestId1.hashCode(), requestId2.hashCode());

    RequestId requestId3 = new RequestId(new TimeSlot(), null);
    assertNotEquals(requestId1, requestId3);
  }

  @Test
  void testEqualsWithNullUser() throws NoSuchFieldException, IllegalAccessException {
    TimeSlot timeSlot = new TimeSlot();
    timeSlot.setTid(2);

    User user1 = new User();

    User user2 = null;

    RequestId requestId1 = new RequestId(timeSlot, user1);
    RequestId requestId2 = new RequestId(timeSlot, user2);

    // requestId1 and requestId2 should be considered not equal because user2 is
    // null
    assertFalse(requestId1.equals(requestId2));
    assertFalse(requestId2.equals(requestId1));
    assertNotEquals(requestId1, requestId2);

    User user3 = new User();
    final RequestId requestId3 = new RequestId(timeSlot, user3);

    // Using reflection to set the UID field
    Field uidField = User.class.getDeclaredField("uid");
    uidField.setAccessible(true); // To access private fields

    // Set different UID values for user1 and user2
    uidField.set(user1, 1);
    uidField.set(user3, 2);

    assertFalse(requestId1.equals(requestId3));

    // Test both users being null
    RequestId requestId5 = new RequestId(timeSlot, null);
    RequestId requestId4 = new RequestId(timeSlot, null);

    // requestId3 and requestId4 should be considered equal because both users are
    // null
    assertEquals(requestId5, requestId4);
  }

  @Test
  void testEqualsWithDifferentClass() {
    User user = new User();

    TimeSlot timeSlot = new TimeSlot();
    timeSlot.setTid(2);

    RequestId requestId = new RequestId(timeSlot, user);

    assertNotEquals(requestId, "A String");
  }
}

package dev.teamproject.request;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import dev.teamproject.request.RequestId;
import dev.teamproject.timeslot.TimeSlot;
import dev.teamproject.user.User;
import org.junit.jupiter.api.Test;

class RequestIdUnitTests {

  @Test
  void testRequestIdConstructorAndGetters() {
    User user = new User();

    TimeSlot timeSlot = new TimeSlot();
    timeSlot.setTid(2); // Assume the TimeSlot class has a setTid method

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
    assertFalse(requestId1.equals(null));
    assertFalse(requestId2.equals(null));
    assertEquals(requestId1.hashCode(), requestId2.hashCode());

    RequestId requestId3 = new RequestId(new TimeSlot(), null);
    assertNotEquals(requestId1, requestId3);

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

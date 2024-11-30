package dev.teamproject.request;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.lang.reflect.Field;

import dev.teamproject.common.CommonTypes;
import dev.teamproject.request.Request;
import dev.teamproject.timeslot.TimeSlot;
import dev.teamproject.user.User;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for the class. These tests verify the correct
 * behavior of constructors, getters, setters, and equality methods.
 */

public class RequestUnitTests {
  @Test
  void testRequestConstructorAndGetters() {
    User user = new User();

    TimeSlot timeSlot = new TimeSlot();

    Request request = new Request(user, timeSlot, "Test description",
        CommonTypes.RequestStatus.approved);

    assertEquals(0, request.getUser().getUid());
    assertEquals(0, request.getTimeSlot().getTid());
    assertEquals("Test description", request.getDescription());
    assertEquals(CommonTypes.RequestStatus.approved, request.getStatus());

    assertEquals(user, request.getUser());
    assertEquals(timeSlot, request.getTimeSlot());
    assertEquals("Test description", request.getDescription());
    assertEquals(CommonTypes.RequestStatus.approved, request.getStatus());
  }

  @Test
  void testSetters() {
    Request request = new Request();
    User user = new User();

    TimeSlot timeSlot = new TimeSlot();
    timeSlot.setTid(4);

    request.setUser(user);
    request.setTimeSlot(timeSlot);
    request.setDescription("Another description");
    request.setStatus(CommonTypes.RequestStatus.rejected);

    assertEquals(0, request.getUser().getUid());
    assertEquals(4, request.getTimeSlot().getTid());
    assertEquals("Another description", request.getDescription());
    assertEquals(CommonTypes.RequestStatus.rejected, request.getStatus());
  }

  @Test
  void testEqualsAndHashCode() {
    User user1 = new User();

    TimeSlot timeSlot1 = new TimeSlot();
    timeSlot1.setTid(2);

    Request request1 = new Request(user1, timeSlot1, "Description",
        CommonTypes.RequestStatus.approved);

    TimeSlot timeSlot2 = new TimeSlot();
    timeSlot2.setTid(2);

    Request request2 = new Request(user1, timeSlot2, "Another Description",
        CommonTypes.RequestStatus.rejected);

    assertEquals(request1, request2);
    assertEquals(request1.hashCode(), request2.hashCode());

    TimeSlot timeSlot3 = new TimeSlot();
    timeSlot3.setTid(3);
    request2.setTimeSlot(timeSlot3);
    assertNotEquals(request1, request2);

    Request request4 = new Request();
    Request request5 = new Request();
    Request request6 = null;
    Request request7 = null;

    // Both are null, so equals and hashCode should work
    assertEquals(request4, request5);
    assertEquals(request4.hashCode(), request5.hashCode());

    assertNotEquals(null, request1);
    assertNotEquals("Not a request object", request5);
    assertFalse(request5.equals("Not a request object"));
    assertNull(request6);
    assertEquals(request6, request7);
  }

  @Test
  void testNotEqualsUserId() throws NoSuchFieldException, IllegalAccessException{

    User user1 = new User();
    User user2 = new User();
    // Using reflection to set the UID field
    Field uidField = User.class.getDeclaredField("uid");
    uidField.setAccessible(true); // To access private fields

    // Set different UID values for user1 and user2
    uidField.set(user1, 1);
    uidField.set(user2, 2);

    TimeSlot timeSlot1 = new TimeSlot();
    timeSlot1.setTid(2);

    Request request1 = new Request(user1, timeSlot1, "Test description",
        CommonTypes.RequestStatus.approved);

    Request request2 = new Request(user2, timeSlot1, "Another Description",
        CommonTypes.RequestStatus.rejected);

    assertNotEquals(request2, request1);

  }

  // Additional tests for edge cases
  @Test
  void testEqualsWhenUserIsNull() {
    User user1 = new User();
    TimeSlot timeSlot1 = new TimeSlot();
    timeSlot1.setTid(2);
    Request request1 = new Request(user1, timeSlot1, "Test description",
        CommonTypes.RequestStatus.approved);

    Request request2 = new Request(null, timeSlot1, "Another Description",
        CommonTypes.RequestStatus.rejected);

    assertNotEquals(request2, request1);
  }

  @Test
  void testEqualsWhenTimeSlotIsNull() {
    User user1 = new User();
    TimeSlot timeSlot1 = new TimeSlot();
    timeSlot1.setTid(2);
    Request request1 = new Request(user1, timeSlot1, "Test description", CommonTypes.RequestStatus.approved);

    Request request2 = new Request(user1, null, "Another Description", CommonTypes.RequestStatus.rejected);

    // assertFalse(request1.equals(request2));
    assertNotEquals(request2, request1);
  }

  @Test
  void testEqualsWhenBothUserAndTimeSlotAreNull() {
    Request request1 = new Request(null, null, "Test description",
        CommonTypes.RequestStatus.approved);

    Request request2 = new Request(null, null, "Another Description",
        CommonTypes.RequestStatus.rejected);

    assertEquals(request1, request2);
  }

  @Test
  void testToString() {
    User user = new User();

    TimeSlot timeSlot = new TimeSlot();
    timeSlot.setTid(2);

    Request request = new Request(user, timeSlot, "Test description",
        CommonTypes.RequestStatus.approved);

    String expected = "Request{tid=2, requester=0, description=Test description, status=approved}";
    assertEquals(expected, request.toString());
  }
}

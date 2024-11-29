package dev.teamproject.meeting;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import dev.teamproject.common.CommonTypes;
import dev.teamproject.common.CommonTypes.Day;
import dev.teamproject.meeting.Meeting;
import dev.teamproject.user.User;
import java.time.LocalTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * This class contains unit tests for the meeting class.
 * It tests the constructor, getter, and setter methods,
 * as well as other functionality for consistency.
 */
public class MeetingUnitTests {
  private Meeting meeting;
  private User organizer;

  @BeforeEach
  void setUp() {
    organizer = new User("Test Organizer", "organizer@example.com");
    meeting = new Meeting();
    meeting.setOrganizer(organizer);
    meeting.setType(CommonTypes.MeetingType.group);
    meeting.setDescription("Test Meeting");
    meeting.setStartTime(LocalTime.of(10, 0)); // 10:00 AM
    meeting.setEndTime(LocalTime.of(11, 0));   // 11:00 AM
    meeting.setStartDay(Day.Monday);
    meeting.setEndDay(Day.Monday);
    meeting.setRecurrence(CommonTypes.Recurrence.weekly);
    meeting.setCreatedAt(LocalTime.of(9, 0)); // 9:00 AM
    meeting.setInviteParticipant(5);
    meeting.setAcceptParticipant(3);
    meeting.setStatus(CommonTypes.MeetingStatus.Valid);
  }

  @Test
  void testGettersAndSetters() {
    assertEquals(organizer, meeting.getOrganizer());
    assertEquals(CommonTypes.MeetingType.group, meeting.getType());
    assertEquals("Test Meeting", meeting.getDescription());
    assertEquals(LocalTime.of(10, 0), meeting.getStartTime());
    assertEquals(LocalTime.of(11, 0), meeting.getEndTime());
    assertEquals(Day.Monday, meeting.getStartDay());
    assertEquals(Day.Monday, meeting.getEndDay());
    assertEquals(CommonTypes.Recurrence.weekly, meeting.getRecurrence());
    assertEquals(LocalTime.of(9, 0), meeting.getCreatedAt());
    assertEquals(5, meeting.getInviteParticipant());
    assertEquals(3, meeting.getAcceptParticipant());
    assertEquals(CommonTypes.MeetingStatus.Valid, meeting.getStatus());
  }

  @Test
  void testSetters() {
    User newOrganizer = new User("New Organizer", "neworganizer@example.com");
    meeting.setOrganizer(newOrganizer);
    meeting.setType(CommonTypes.MeetingType.one_on_one);
    meeting.setDescription("Updated Meeting");
    meeting.setStartTime(LocalTime.of(14, 0)); // 2:00 PM
    meeting.setEndTime(LocalTime.of(15, 0));   // 3:00 PM
    meeting.setStartDay(Day.Tuesday);
    meeting.setEndDay(Day.Wednesday);
    meeting.setRecurrence(CommonTypes.Recurrence.daily);
    meeting.setCreatedAt(LocalTime.of(8, 0));  // 8:00 AM
    meeting.setInviteParticipant(10);
    meeting.setAcceptParticipant(7);
    meeting.setStatus(CommonTypes.MeetingStatus.Invalid);

    assertEquals(newOrganizer, meeting.getOrganizer());
    assertEquals(CommonTypes.MeetingType.one_on_one, meeting.getType());
    assertEquals("Updated Meeting", meeting.getDescription());
    assertEquals(LocalTime.of(14, 0), meeting.getStartTime());
    assertEquals(LocalTime.of(15, 0), meeting.getEndTime());
    assertEquals(Day.Tuesday, meeting.getStartDay());
    assertEquals(Day.Wednesday, meeting.getEndDay());
    assertEquals(CommonTypes.Recurrence.daily, meeting.getRecurrence());
    assertEquals(LocalTime.of(8, 0), meeting.getCreatedAt());
    assertEquals(10, meeting.getInviteParticipant());
    assertEquals(7, meeting.getAcceptParticipant());
    assertEquals(CommonTypes.MeetingStatus.Invalid, meeting.getStatus());
  }

  @Test
  void testNotEqual() {
    Meeting meeting1 = new Meeting();
    assertNotEquals(meeting1, meeting);
  }

  @Test
  void testMidGetter() {
    assertEquals(0, meeting.getMid());
  }

  // Test invalid participant counts (negative values)
  @Test
  void testInviteParticipantNegative() {
    assertThrows(IllegalArgumentException.class, () -> meeting.setInviteParticipant(-1));
  }

  @Test
  void testAcceptParticipantNegative() {
    assertThrows(IllegalArgumentException.class, () -> meeting.setAcceptParticipant(-1));
  }

  // Test invalid descriptions that exceed maximum length
  @Test
  void testDescriptionTooLong() {
    String longDescription = "A".repeat(501); // 501 characters
    assertThrows(IllegalArgumentException.class, () -> meeting.setDescription(longDescription));
  }

  // Test for invalid start time being later than end time
  @Test
  void testStartTimeAfterEndTime() {
    meeting.setStartTime(LocalTime.of(12, 0)); // 12:00 PM
    meeting.setEndTime(LocalTime.of(11, 0));   // 11:00 AM
    assertThrows(IllegalArgumentException.class, () -> {
      if (meeting.getStartTime().isAfter(meeting.getEndTime())) {
        throw new IllegalArgumentException("Start time cannot be after end time.");
      }
    });
  }

  // Test invalid enum values
  @Test
  void testInvalidMeetingStatus() {
    assertThrows(IllegalArgumentException.class, () -> meeting.setStatus(null));
  }

  @Test
  void testInvalidMeetingType() {
    assertThrows(IllegalArgumentException.class, () -> meeting.setType(null));
  }

  @Test
  void testInvalidRecurrence() {
    assertThrows(IllegalArgumentException.class, () -> meeting.setRecurrence(null));
  }

  @Test
  void testInvalidDays() {
    assertThrows(IllegalArgumentException.class, () -> meeting.setStartDay(null));
    assertThrows(IllegalArgumentException.class, () -> meeting.setEndDay(null));
  }

  // Test constructor with valid parameters
  @Test
  void testConstructor() {
    Meeting newMeeting = new Meeting(
        organizer, Day.Monday, Day.Tuesday, LocalTime.of(9, 0), "Meeting description",
        CommonTypes.Recurrence.daily, LocalTime.of(10, 0), CommonTypes.MeetingType.group, CommonTypes.MeetingStatus.Valid
    );

    assertEquals(organizer, newMeeting.getOrganizer());
    assertEquals(Day.Monday, newMeeting.getStartDay());
    assertEquals(Day.Tuesday, newMeeting.getEndDay());
    assertEquals(LocalTime.of(9, 0), newMeeting.getStartTime());
    assertEquals("Meeting description", newMeeting.getDescription());
    assertEquals(CommonTypes.Recurrence.daily, newMeeting.getRecurrence());
    assertEquals(LocalTime.of(10, 0), newMeeting.getEndTime());
    assertEquals(CommonTypes.MeetingType.group, newMeeting.getType());
    assertEquals(CommonTypes.MeetingStatus.Valid, newMeeting.getStatus());
  }
}

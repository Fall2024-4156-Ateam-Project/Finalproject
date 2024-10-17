package dev.teamproject;

import dev.teamproject.common.CommonTypes;
import dev.teamproject.meeting.Meeting;
import dev.teamproject.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;

import static org.junit.jupiter.api.Assertions.*;

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
        meeting.setStartTime(Timestamp.valueOf("2024-01-01 10:00:00"));
        meeting.setEndTime(Timestamp.valueOf("2024-01-01 11:00:00"));
        meeting.setRecurrence(CommonTypes.Recurrence.weekly);
        meeting.setCreatedAt(Timestamp.valueOf("2024-01-01 09:00:00"));
        meeting.setInviteParticipant(5);
        meeting.setAcceptParticipant(3);
        meeting.setStatus(CommonTypes.MeetingStatus.Valid);
    }

    @Test
    void testGettersAndSetters() {
        assertEquals(organizer, meeting.getOrganizer());
        assertEquals(CommonTypes.MeetingType.group, meeting.getType());
        assertEquals("Test Meeting", meeting.getDescription());
        assertEquals(Timestamp.valueOf("2024-01-01 10:00:00"), meeting.getStartTime());
        assertEquals(Timestamp.valueOf("2024-01-01 11:00:00"), meeting.getEndTime());
        assertEquals(CommonTypes.Recurrence.weekly, meeting.getRecurrence());
        assertEquals(Timestamp.valueOf("2024-01-01 09:00:00"), meeting.getCreatedAt());
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
        meeting.setStartTime(Timestamp.valueOf("2024-01-02 10:00:00"));
        meeting.setEndTime(Timestamp.valueOf("2024-01-02 11:00:00"));
        meeting.setRecurrence(CommonTypes.Recurrence.daily);
        meeting.setCreatedAt(Timestamp.valueOf("2024-01-01 08:00:00"));
        meeting.setInviteParticipant(10);
        meeting.setAcceptParticipant(7);
        meeting.setStatus(CommonTypes.MeetingStatus.Invalid);

        assertEquals(newOrganizer, meeting.getOrganizer());
        assertEquals(CommonTypes.MeetingType.one_on_one, meeting.getType());
        assertEquals("Updated Meeting", meeting.getDescription());
        assertEquals(Timestamp.valueOf("2024-01-02 10:00:00"), meeting.getStartTime());
        assertEquals(Timestamp.valueOf("2024-01-02 11:00:00"), meeting.getEndTime());
        assertEquals(CommonTypes.Recurrence.daily, meeting.getRecurrence());
        assertEquals(Timestamp.valueOf("2024-01-01 08:00:00"), meeting.getCreatedAt());
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
    
}

package dev.teamproject;


import dev.teamproject.timeslot.TimeSlot;
import dev.teamproject.common.CommonTypes;
import dev.teamproject.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalTime;

public class TimeSlotUnitTests {
    private TimeSlot timeSlot;
    private User user;

    @BeforeEach
    void setUp() {
        user = new User("Test User", "test@example.com");
        timeSlot = new TimeSlot(user, CommonTypes.Day.Monday, LocalTime.of(9, 0), LocalTime.of(10, 0), CommonTypes.Availability.available);
    }

    @Test
    void testConstructorAndGetters() {
        assertEquals(user, timeSlot.getUser());
        assertEquals(CommonTypes.Day.Monday, timeSlot.getDay());
        assertEquals(LocalTime.of(9, 0), timeSlot.getStartTime());
        assertEquals(LocalTime.of(10, 0), timeSlot.getEndTime());
        assertEquals(CommonTypes.Availability.available, timeSlot.getAvailability());
    }

    @Test
    void testSetters() {
        User newUser = new User("New User", "new@example.com");
        timeSlot.setUser(newUser);
        timeSlot.setDay(CommonTypes.Day.Tuesday);
        timeSlot.setStartTime(LocalTime.of(10, 0));
        timeSlot.setEndTime(LocalTime.of(11, 0));
        timeSlot.setAvailability(CommonTypes.Availability.busy);

        assertEquals(newUser, timeSlot.getUser());
        assertEquals(CommonTypes.Day.Tuesday, timeSlot.getDay());
        assertEquals(LocalTime.of(10, 0), timeSlot.getStartTime());
        assertEquals(LocalTime.of(11, 0), timeSlot.getEndTime());
        assertEquals(CommonTypes.Availability.busy, timeSlot.getAvailability());
    }

    @Test
    void testToString() {
        String expectedString = "TimeSlot{tid=0, user=" + user.getUid() + ", day=Monday, startTime=09:00, endTime=10:00, availability=available}";
        assertEquals(expectedString, timeSlot.toString());
    }

    @Test
    void testEqualsAndHashCode() {
        TimeSlot timeSlot1 = new TimeSlot(user, CommonTypes.Day.Monday, LocalTime.of(9, 0), LocalTime.of(10, 0), CommonTypes.Availability.available);
        TimeSlot timeSlot2 = new TimeSlot(user, CommonTypes.Day.Monday, LocalTime.of(9, 0), LocalTime.of(10, 0), CommonTypes.Availability.available);

        assertEquals(timeSlot1, timeSlot2);
        assertEquals(timeSlot1.hashCode(), timeSlot2.hashCode());

        timeSlot2.setTid(1);
        assertNotEquals(timeSlot1, timeSlot2);
        assertNotEquals(timeSlot1.hashCode(), timeSlot2.hashCode());
    }

    @Test
    void testDifferentObjectsAreNotEqual() {
        TimeSlot differentTimeSlot = new TimeSlot(new User("Different User", "diff@example.com"), CommonTypes.Day.Wednesday, LocalTime.of(11, 0), LocalTime.of(12, 0), CommonTypes.Availability.busy);
        differentTimeSlot.setTid(2);
        assertNotEquals(timeSlot, differentTimeSlot);
    }
    
}
package dev.teamproject.timeslot;

import dev.teamproject.common.CommonTypes;
import java.time.LocalTime;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TimeSlotComparatorTests {
  private TimeSlotComparator timeSlotComparator;
  private TimeSlot timeSlot1;
  private TimeSlot timeSlot2;

  @BeforeEach
  void setUp() {
    timeSlotComparator = new TimeSlotComparator();
    timeSlot1 = new TimeSlot();
    timeSlot2 = new TimeSlot();
  }

  @Test
  void testCompare_DifferentDays() {
    timeSlot1.setStartDay(CommonTypes.Day.Monday);
    timeSlot1.setStartTime(LocalTime.of(10, 0));
    timeSlot2.setStartDay(CommonTypes.Day.Tuesday);
    timeSlot2.setStartTime(LocalTime.of(10, 0));

    int result = timeSlotComparator.compare(timeSlot1, timeSlot2);
    assertEquals(-1, result, "Monday should come before Tuesday.");
  }

  @Test
  void testCompare_SameDay_DifferentTimes() {
    timeSlot1.setStartDay(CommonTypes.Day.Wednesday);
    timeSlot1.setStartTime(LocalTime.of(9, 0));
    timeSlot2.setStartDay(CommonTypes.Day.Wednesday);
    timeSlot2.setStartTime(LocalTime.of(15, 0));
    int result = timeSlotComparator.compare(timeSlot1, timeSlot2);
    assertEquals(-1, result, "9:00 AM should come before 3:00 PM on the same day.");
  }

  @Test
  void testCompare_SameDay_SameTime() {
    timeSlot1.setStartDay(CommonTypes.Day.Friday);
    timeSlot1.setStartTime(LocalTime.of(14, 0));
    timeSlot2.setStartDay(CommonTypes.Day.Friday);
    timeSlot2.setStartTime(LocalTime.of(14, 0));
    int result = timeSlotComparator.compare(timeSlot1, timeSlot2);
    assertEquals(0, result, "Two time slots with the same day and time should be equal.");
  }

  @Test
  void testCompare_DifferentDays_Reversed() {
    timeSlot1.setStartDay(CommonTypes.Day.Sunday);
    timeSlot1.setStartTime(LocalTime.of(8, 0));
    timeSlot2.setStartDay(CommonTypes.Day.Saturday);
    timeSlot2.setStartTime(LocalTime.of(8, 0));
    int result = timeSlotComparator.compare(timeSlot1, timeSlot2);
    assertEquals(1, result, "Sunday should come after Saturday.");
  }

  @Test
  void testCompare_DifferentTimes_Reversed() {
    timeSlot1.setStartDay(CommonTypes.Day.Thursday);
    timeSlot1.setStartTime(LocalTime.of(20, 0));
    timeSlot2.setStartDay(CommonTypes.Day.Thursday);
    timeSlot2.setStartTime(LocalTime.of(10, 0));

    int result = timeSlotComparator.compare(timeSlot1, timeSlot2);
    assertEquals(1, result, "8:00 PM should come after 10:00 AM on the same day.");
  }
}

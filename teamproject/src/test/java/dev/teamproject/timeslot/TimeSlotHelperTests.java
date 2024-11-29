package dev.teamproject.timeslot;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import dev.teamproject.common.CommonTypes;
import dev.teamproject.common.Pair;
import dev.teamproject.timeslot.TimeSlot;
import dev.teamproject.timeslot.TimeSlotHelper;

import java.time.LocalTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.cglib.core.Local;

public class TimeSlotHelperTests {
  @InjectMocks
  private TimeSlotHelper timeSlotHelper;

  private TimeSlot timeSlot1, timeSlot2;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);

    timeSlot1 = new TimeSlot();
    timeSlot1.setStartDay(CommonTypes.Day.Monday);
    timeSlot1.setStartTime(LocalTime.of(10, 0));
    timeSlot1.setEndDay(CommonTypes.Day.Monday);
    timeSlot1.setEndTime(LocalTime.of(12, 0));

    timeSlot2 = new TimeSlot();
    timeSlot2.setStartDay(CommonTypes.Day.Monday);
    timeSlot2.setStartTime(LocalTime.of(11, 0));
    timeSlot2.setEndDay(CommonTypes.Day.Monday);
    timeSlot2.setEndTime(LocalTime.of(13, 0));
  }

  @Test
  void testAbsTime() {
    LocalTime time = LocalTime.of(12, 0);
    CommonTypes.Day day = CommonTypes.Day.Thursday;

    int minInDay = time.getHour() * 60 + time.getMinute();
    int minInWeek = day.ordinal() * 24 * 60;
    int expectedVal = minInDay + minInWeek;

    assertEquals(expectedVal, timeSlotHelper.absTime(day, time));
  }

  @Test
  void testIsOverlapped() {
    
    // Exact match overlap
    timeSlot1.setEndTime(LocalTime.of(12, 0));
    timeSlot2.setStartTime(LocalTime.of(12, 0));
    assertFalse(timeSlotHelper.isOverlapped(timeSlot1, timeSlot2));

    // No overlap
    timeSlot2.setStartTime(LocalTime.of(13, 0));
    assertFalse(timeSlotHelper.isOverlapped(timeSlot1, timeSlot2));

    // Wrapping overlap
    timeSlot1.setEndDay(CommonTypes.Day.Sunday);
    timeSlot1.setEndTime(LocalTime.of(23, 59));
    timeSlot2.setStartDay(CommonTypes.Day.Monday);
    timeSlot2.setStartTime(LocalTime.of(0, 0));
    assertTrue(timeSlotHelper.isOverlapped(timeSlot1, timeSlot2));

    //t1 wrap, t2 no wrap, overlap
    timeSlot1.setEndDay(CommonTypes.Day.Monday);
    timeSlot1.setEndTime(LocalTime.of(9, 0));
    timeSlot2.setEndDay(CommonTypes.Day.Sunday);
    timeSlot2.setEndTime(LocalTime.of(23, 59));
    assertTrue(timeSlotHelper.isOverlapped(timeSlot1, timeSlot2));

    //t1 wrap, t2 no wrap, no overlap
    // timeSlot1.setEndTime(LocalTime.of(11, 0));
    timeSlot2.setEndDay(CommonTypes.Day.Monday);
    timeSlot2.setEndTime(LocalTime.of(2, 0));
    assertTrue(timeSlotHelper.isOverlapped(timeSlot1, timeSlot2));

    //t1 no wrap, t2 no wrap, overlap
    timeSlot1.setEndTime(LocalTime.of(11, 0));
    timeSlot2.setEndDay(CommonTypes.Day.Sunday);
    timeSlot2.setEndTime(LocalTime.of(23, 59));
    assertTrue(timeSlotHelper.isOverlapped(timeSlot1, timeSlot2));
    
  }

  @Test
  void testIsWrapped() {

    timeSlot1.setEndDay(CommonTypes.Day.Sunday);
    timeSlot1.setEndTime(LocalTime.of(5, 0));
    assertEquals(false, timeSlotHelper.isWrapped(timeSlot1));

    timeSlot1.setEndDay(CommonTypes.Day.Monday);
    timeSlot1.setEndTime(LocalTime.of(4, 0));
    assertEquals(true, timeSlotHelper.isWrapped(timeSlot1));
  }

  @Test
  void testIsEarlier() {

    CommonTypes.Day d1 = CommonTypes.Day.Tuesday;
    LocalTime t1 = LocalTime.of(0, 0, 0);
    CommonTypes.Day d2 = CommonTypes.Day.Tuesday;
    LocalTime t2 = LocalTime.of(2, 0, 34);
    CommonTypes.Day d3 = CommonTypes.Day.Monday;
    LocalTime t3 = LocalTime.of(14, 0, 0);

    assertEquals(true, timeSlotHelper.isEarlier(d1,t1,d2,t2));
    assertEquals(false, timeSlotHelper.isEarlier(d1,t1,d3,t3));
  }

  @Test
  void testIsLater() {
    CommonTypes.Day d1 = CommonTypes.Day.Tuesday;
    LocalTime t1 = LocalTime.of(10, 0);
    CommonTypes.Day d2 = CommonTypes.Day.Wednesday;
    LocalTime t2 = LocalTime.of(9, 0);

    assertEquals(false, timeSlotHelper.isLater(d1, t1, d2, t2));
    assertEquals(true, timeSlotHelper.isLater(d2, t2, d1, t1));
  }

  @Test
  void testGetDayAndTimeFromAbs() {
    int absTime = 24 * 60 + 150; // Tuesday, 02:30 AM
    Pair<CommonTypes.Day, LocalTime> expResult = new Pair<>(CommonTypes.Day.Tuesday, LocalTime.of(2, 30));
    Pair<CommonTypes.Day, LocalTime> result = timeSlotHelper.getDayAndTimeFromAbs(absTime);

    assertEquals(expResult, result);
  }

  @Test
  void testGetDayFromIndex() {
    assertEquals(CommonTypes.Day.Monday, timeSlotHelper.getDayFromIndex(0));
    assertEquals(CommonTypes.Day.Tuesday, timeSlotHelper.getDayFromIndex(1));
    assertEquals(CommonTypes.Day.Wednesday, timeSlotHelper.getDayFromIndex(2));
    assertEquals(CommonTypes.Day.Thursday, timeSlotHelper.getDayFromIndex(3));
    assertEquals(CommonTypes.Day.Friday, timeSlotHelper.getDayFromIndex(4));
    assertEquals(CommonTypes.Day.Saturday, timeSlotHelper.getDayFromIndex(5));
    assertEquals(CommonTypes.Day.Sunday, timeSlotHelper.getDayFromIndex(6));
    assertThrows(IllegalArgumentException.class, () -> timeSlotHelper.getDayFromIndex(7));
    assertThrows(IllegalArgumentException.class, () -> timeSlotHelper.getDayFromIndex(-1));
  }    

  @Test
  void testGetWeekStartAndEnd() {
    Pair<CommonTypes.Day, LocalTime> weekStart = timeSlotHelper.getWeekStart();
    Pair<CommonTypes.Day, LocalTime> weekEnd = timeSlotHelper.getWeekEnd();

    assertEquals(new Pair<>(CommonTypes.Day.Monday, LocalTime.of(0, 0)), weekStart);
    assertEquals(new Pair<>(CommonTypes.Day.Sunday, LocalTime.of(23, 59)), weekEnd);

    assertEquals(CommonTypes.Day.Monday, weekStart.getKey());
    assertEquals(LocalTime.of(0, 0), weekStart.getValue());
    assertEquals(CommonTypes.Day.Sunday, weekEnd.getKey());
    assertEquals(LocalTime.of(23, 59), weekEnd.getValue());
  }
}


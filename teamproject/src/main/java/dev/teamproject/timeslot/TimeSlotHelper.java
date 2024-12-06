package dev.teamproject.timeslot;

import dev.teamproject.common.CommonTypes;
import dev.teamproject.common.CommonTypes.Day;
import dev.teamproject.common.Pair;
import java.time.LocalTime;
import org.springframework.stereotype.Component;

/**
 * Utility class for working with time slots. This class provides methods to determine
 * whether two time slots overlap, convert time to absolute time in the week, and other
 * helper functions related to managing time slots.
 */
@Component
public class TimeSlotHelper {

  public TimeSlotHelper() {
  }

  public Pair<Day, LocalTime> getWeekEnd() {
    return weekEnd;
  }

  public Pair<Day, LocalTime> getWeekStart() {
    return weekStart;
  }

  private final Pair<CommonTypes.Day, LocalTime> weekEnd = this.getDayAndTimeFromAbs(
      7 * 24 * 60 - 1);

  private final Pair<CommonTypes.Day, LocalTime> weekStart = this.getDayAndTimeFromAbs(
      0);


  /**
   * Determines whether two time slots overlap. The method compares the start and end times of
   * two time slots and checks if there is any overlap.
   *
   * @param t1 the first time slot
   * @param t2 the second time slot
   * @return {@code true} if the time slots overlap, {@code false} otherwise
   */
  public boolean isOverlapped(TimeSlot t1, TimeSlot t2) {
    // if the t1 end time before t2 start
    // exclusive

    int absStartTimeT1 = absTime(t1.getStartDay(), t1.getStartTime());
    int absEndTimeT1 = absTime(t1.getEndDay(), t1.getEndTime());
    int absStartTimeT2 = absTime(t2.getStartDay(), t2.getStartTime());
    int absEndTimeT2 = absTime(t2.getEndDay(), t2.getEndTime());

    // checks if end time in the next week
    if (absStartTimeT1 > absEndTimeT1) {
      absEndTimeT1 += 24 * 60 * 7;
    }
    if (absStartTimeT2 > absEndTimeT2) {
      absEndTimeT2 += 24 * 60 * 7;
    }

    // if both wrap around or both not wrap
    if ((absEndTimeT1 <= 24 * 60 * 7 && absEndTimeT2 <= 24 * 60 * 7) || (absEndTimeT1 > 24 * 60 * 7
        && absEndTimeT2 > 24 * 60 * 7)) {
      return !(absEndTimeT1 <= absStartTimeT2 || absEndTimeT2 <= absStartTimeT1);
    }
    // if wrap and one not
    // check if the wrap

    // T1 wrap, T2 not
    if (absEndTimeT1 > 24 * 60 * 7) {
      // overlapped
      if (absStartTimeT2 < absEndTimeT1 - 24 * 60 * 7) {
        return true;
      }
      if (absEndTimeT2 > absStartTimeT1) {
        return true;
      }
    }
    // T2 wrap, T1 not
    if (absEndTimeT2 > 24 * 60 * 7) {
      if (absStartTimeT1 < absEndTimeT2 - 24 * 60 * 7) {
        return true;
      }
      if (absEndTimeT1 > absStartTimeT2) {
        return true;
      }
    }
    return false;
  }

  /**
   * Converts a given day and time to absolute time in the week (in minutes). The absolute time
   * represents the number of minutes elapsed since the start of the week.
   *
   * @param day the day of the week
   * @param time the time of the day
   * @return the absolute time in minutes since the start of the week
   */
  public int absTime(CommonTypes.Day day, LocalTime time) {
    int minInDay = time.getHour() * 60 + time.getMinute();
    int minInWeek = day.ordinal() * 24 * 60;
    return minInDay + minInWeek;
  }

  /**
   * Checks if the given time slot wraps around the week boundary (i.e., starts before it ends).
   *
   * @param t the time slot to check
   * @return {@code true} if the time slot wraps, {@code false} otherwise
   */
  public Boolean isWrapped(TimeSlot t) {
    int absEndTimeT = absTime(t.getEndDay(), t.getEndTime());
    int absStartTimeT = absTime(t.getStartDay(), t.getStartTime());
    return absStartTimeT > absEndTimeT;
  }

  public boolean isEarlier(CommonTypes.Day d1, LocalTime t1, CommonTypes.Day d2, LocalTime t2) {
    return absTime(d1, t1) < absTime(d2, t2);
  }

  public boolean isLater(CommonTypes.Day d1, LocalTime t1, CommonTypes.Day d2, LocalTime t2) {
    return absTime(d1, t1) > absTime(d2, t2);
  }

  /**
   * Converts an absolute time (in minutes since the start of the week) into a {@link Pair}
   * of {@link Day} and {@link LocalTime}.
   *
   * @param absTime the absolute time in minutes
   * @return a pair representing the day and time corresponding to the given absolute time
   */
  public Pair<Day, LocalTime> getDayAndTimeFromAbs(int absTime) {
    int dayIndex = absTime / (24 * 60) % 7;
    int minutes = absTime % (24 * 60);
    return new Pair<>(getDayFromIndex(dayIndex), LocalTime.of(minutes / 60, minutes % 60));
  }

  /**
   * Converts an index into a corresponding {@link Day}.
   *
   * @param index the index representing a day (0-6)
   * @return the corresponding {@link Day}
   * @throws IllegalArgumentException if the index is invalid
   */
  public CommonTypes.Day getDayFromIndex(int index) {
    switch (index) {
      case 0:
        return CommonTypes.Day.Monday;
      case 1:
        return CommonTypes.Day.Tuesday;
      case 2:
        return CommonTypes.Day.Wednesday;
      case 3:
        return CommonTypes.Day.Thursday;
      case 4:
        return CommonTypes.Day.Friday;
      case 5:
        return CommonTypes.Day.Saturday;
      case 6:
        return CommonTypes.Day.Sunday;
      default:
        throw new IllegalArgumentException("Invalid day index: " + index);
    }
  }
}


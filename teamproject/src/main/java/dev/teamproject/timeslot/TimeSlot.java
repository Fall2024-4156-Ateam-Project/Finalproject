package dev.teamproject.timeslot;

import dev.teamproject.common.CommonTypes;
import dev.teamproject.common.CommonTypes.Day;
import dev.teamproject.user.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalTime;
import java.util.Objects;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 * Represents a time slot for a user, including availability and scheduling information.
 */

@Entity
@Table(name = "TimeSlot")
public class TimeSlot {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "TID")
  private int tid;

  @ManyToOne
  @JoinColumn(name = "UID", nullable = false)
  private User user;

//  @Enumerated(EnumType.STRING)
//  @Column(name = "day", nullable = false)
//  private CommonTypes.Day day;

  public Day getStartDay() {
    return startDay;
  }

  public void setStartDay(Day startDay) {
    this.startDay = startDay;
  }

  public Day getEndDay() {
    return endDay;
  }

  public void setEndDay(Day endDay) {
    this.endDay = endDay;
  }

  @Enumerated(EnumType.STRING)
  @Column(name = "startDay", nullable = false)
  private CommonTypes.Day startDay;
  @Enumerated(EnumType.STRING)
  @Column(name = "endDay", nullable = false)
  private CommonTypes.Day endDay;

  @Column(name = "start_time", nullable = false)
  private LocalTime startTime;

  @Column(name = "end_time", nullable = false)
  private LocalTime endTime;

  @Enumerated(EnumType.STRING)
  @Column(name = "availability", nullable = false)
  private CommonTypes.Availability availability;

  // Constructors
  public TimeSlot() {
  }

  /**
   * construct a time slot given user day, information of time and availability.
   */

  public TimeSlot(User user, CommonTypes.Day day, CommonTypes.Day startDay, CommonTypes.Day endDay,
      LocalTime startTime,
      LocalTime endTime, CommonTypes.Availability availability) {
    this.user = user;
//    this.day = day;
    this.startDay = startDay;
    this.endDay = endDay;
    this.startTime = startTime;
    this.endTime = endTime;
    this.availability = availability;
  }

  // Getters and Setters
  public int getTid() {
    return tid;
  }

  public void setTid(int tid) {
    this.tid = tid;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

//  public CommonTypes.Day getDay() {
//    return day;
//  }

//  public void setDay(CommonTypes.Day day) {
//    this.day = day;
//  }

  public LocalTime getStartTime() {
    return startTime;
  }

  public void setStartTime(LocalTime startTime) {
    this.startTime = startTime;
  }

  public LocalTime getEndTime() {
    return endTime;
  }

  public void setEndTime(LocalTime endTime) {
    this.endTime = endTime;
  }

  public CommonTypes.Availability getAvailability() {
    return availability;
  }

  public void setAvailability(CommonTypes.Availability availability) {
    this.availability = availability;
  }

  // toString
  @Override
  public String toString() {
    return "TimeSlot{"
        + "tid=" + tid
        + ", user=" + user.getUid()
        + ", startDay=" + startDay
        + ", endDay=" + endDay
        + ", startTime=" + startTime
        + ", endTime=" + endTime
        + ", availability=" + availability
        + '}';
  }

  // equals and hashCode
  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    TimeSlot timeSlot = (TimeSlot) o;

    return tid == timeSlot.tid;
  }

  @Override
  public int hashCode() {
    return Objects.hash(tid);
  }
}


class TimeSlotComparator implements java.util.Comparator<TimeSlot> {

  @Override
  public int compare(TimeSlot t1, TimeSlot t2) {
    int dayCompare = t1.getStartDay().compareTo(t2.getStartDay());
    if (dayCompare != 0) {
      return dayCompare;
    }
    // compare time iff they are on same day
    return t1.getStartTime().compareTo(t2.getStartTime());
  }
}


@Component
class TimeSlotHelper {

  /**
   * Is two timeslots overlapped
   *
   * @param t1
   * @param t2
   * @return
   */
  public boolean isOverlapped(TimeSlot t1, TimeSlot t2) {
    // if the t1 end time before t2 start

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
      return !(absEndTimeT1 < absStartTimeT2 || absEndTimeT2 < absStartTimeT1);
    }
    // if wrap and one not
    // check if the wrap

    // T1 wrap, T2 not
    if (absEndTimeT1 > 24 * 60 * 7){
      // overlapped
      if (absStartTimeT2 <= absEndTimeT1 - 24*60*7 ){
        return true;
      }
      if (absEndTimeT2 >= absStartTimeT1){
        return true;
      }
    }
    // T2 wrap, T1 not
    if (absEndTimeT2 > 24 * 60 * 7){
      if (absStartTimeT1 <= absEndTimeT2 - 24*60*7 ){
        return true;
      }
      if (absEndTimeT1 >= absStartTimeT2){
        return true;
      }
    }
    return false;
  }

  /**
   * This return the abs time in a week, minimum unit is minute
   *
   * @param day
   * @param time
   * @return
   */

  public int absTime(CommonTypes.Day day, LocalTime time) {
    int minInDay = time.getHour() * 60 + time.getMinute();
    int minInWeek = day.ordinal() * 24 * 60;
    return minInDay + minInWeek;
  }
}
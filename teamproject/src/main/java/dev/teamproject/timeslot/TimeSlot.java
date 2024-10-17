package dev.teamproject.timeslot;

import dev.teamproject.common.CommonTypes;
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
  
  @Enumerated(EnumType.STRING)
  @Column(name = "day", nullable = false)
  private CommonTypes.Day day;
  
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

  public TimeSlot(User user, CommonTypes.Day day, LocalTime startTime,
                  LocalTime endTime, CommonTypes.Availability availability) {
    this.user = user;
    this.day = day;
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
  
  public CommonTypes.Day getDay() {
    return day;
  }
  
  public void setDay(CommonTypes.Day day) {
    this.day = day;
  }
  
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
      + ", day=" + day
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

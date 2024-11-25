package dev.teamproject.timeslot;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import dev.teamproject.common.CommonTypes;
import dev.teamproject.common.CommonTypes.Day;
import dev.teamproject.common.Pair;
import dev.teamproject.request.Request;
import dev.teamproject.user.User;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.time.LocalTime;
import java.util.List;
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

  public List<Request> getRequests() {
    return requests;
  }

  public void setRequests(List<Request> requests) {
    this.requests = requests;
  }

  @OneToMany(mappedBy = "timeSlot", cascade = CascadeType.ALL, orphanRemoval = true)
  @JsonIgnoreProperties("timeSlot")
  private List<Request> requests;

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

  public TimeSlot(User user, CommonTypes.Day startDay, CommonTypes.Day endDay,
      LocalTime startTime,
      LocalTime endTime, CommonTypes.Availability availability) {
    this.user = user;
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

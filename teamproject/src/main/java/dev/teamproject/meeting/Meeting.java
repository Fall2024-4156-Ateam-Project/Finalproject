package dev.teamproject.meeting;

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
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalTime;

/**
 * This class represents a database table named Meeting. It defines the structure, relationships,
 * and behavior of meeting records within the application
 */
@Entity
@Table(name = "Meeting")
public class Meeting {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "MID", nullable = false)
  private int mid;

  @ManyToOne
  @JoinColumn(name = "organizer_id", referencedColumnName = "UID")
  @NotNull(message = "Organizer is required")
  private User organizer;

  @Enumerated(EnumType.STRING)
  @Column(name = "type")
  @NotNull(message = "Meeting type is required")
  private CommonTypes.MeetingType type;

  @Column(name = "description", columnDefinition = "TEXT")
  @Size(max = 500, message = "Description should not exceed 500 characters")
  private String description;

  @Column(name = "start_time")
  @NotNull(message = "Start time is required")
  private LocalTime startTime;

  @Column(name = "end_time")
  @NotNull(message = "End time is required")
  private LocalTime endTime;

  @Enumerated(EnumType.STRING)
  @Column(name = "startDay", nullable = false)
  private CommonTypes.Day startDay;
  @Enumerated(EnumType.STRING)
  @Column(name = "endDay", nullable = false)
  private CommonTypes.Day endDay;

  @Enumerated(EnumType.STRING)
  @Column(name = "recurrence")
  private CommonTypes.Recurrence recurrence;

  @Column(name = "created_at")
  private LocalTime createdAt;

  @Column(name = "num_invite_participant")
  @Min(value = 0, message = "Invite participant count cannot be negative")
  private Integer inviteParticipant;

  @Column(name = "num_accept_participant")
  @Min(value = 0, message = "Accept participant count cannot be negative")
  private Integer acceptParticipant;

  @Enumerated(EnumType.STRING)
  @Column(name = "status")
  private CommonTypes.MeetingStatus status;

  public int getMid() {
    return mid;
  }

  public void setMid(int mid) {
    this.mid = mid;
  }

  public User getOrganizer() {
    return organizer;
  }

  public void setOrganizer(User organizer) {
    this.organizer = organizer;
  }

  public CommonTypes.MeetingType getType() {
    return type;
  }

  /**
   * Sets the type of the meeting.
   */
  public void setType(CommonTypes.MeetingType type) {
    if (type == null) {
      throw new IllegalArgumentException("Meeting type is required");
    }
    this.type = type;
  }

  public String getDescription() {
    return description;
  }

  /**
   * Sets the description of the meeting.
   */
  public void setDescription(String description) {
    if (description != null && description.length() > 500) {
      throw new IllegalArgumentException("Description should not exceed 500 characters");
    }
    this.description = description;
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

  public Day getStartDay() {
    return startDay;
  }

  /**
   * Sets the start day of the meeting.
   */
  public void setStartDay(Day startDay) {
    if (startDay == null) {
      throw new IllegalArgumentException("Start day is required");
    }
    this.startDay = startDay;
  }

  public Day getEndDay() {
    return endDay;
  }

  /**
   * Sets the end day of the meeting.
   */
  public void setEndDay(Day endDay) {
    if (endDay == null) {
      throw new IllegalArgumentException("End day is required");
    }
    this.endDay = endDay;
  }

  public CommonTypes.Recurrence getRecurrence() {
    return recurrence;
  }

  /**
   * Sets the recurrence pattern of the meeting.
   */
  public void setRecurrence(CommonTypes.Recurrence recurrence) {
    if (recurrence == null) {
      throw new IllegalArgumentException("Recurrence is required");
    }
    this.recurrence = recurrence;
  }

  public LocalTime getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(LocalTime createdAt) {
    this.createdAt = createdAt;
  }

  public Integer getInviteParticipant() {
    return inviteParticipant;
  }

  /**
   * Sets the number of participants invited to the meeting.
   */
  public void setInviteParticipant(Integer inviteParticipant) {
    if (inviteParticipant != null && inviteParticipant < 0) {
      throw new IllegalArgumentException("Invite participant count cannot be negative");
    }
    this.inviteParticipant = inviteParticipant;
  }

  public Integer getAcceptParticipant() {
    return acceptParticipant;
  }

  /**
   * Sets the number of participants who accepted the meeting.
   */
  public void setAcceptParticipant(Integer acceptParticipant) {
    if (acceptParticipant != null && acceptParticipant < 0) {
      throw new IllegalArgumentException("Accept participant count cannot be negative");
    }
    this.acceptParticipant = acceptParticipant;
  }

  public CommonTypes.MeetingStatus getStatus() {
    return status;
  }

  /**
   * Sets the status of meeting.
   */
  public void setStatus(CommonTypes.MeetingStatus status) {
    if (status == null) {
      throw new IllegalArgumentException("Meeting status is required");
    }
    this.status = status;
  }

  // Constructors
  public Meeting() {
  }

  /**
   * construct a time slot given user day, information of time and availability.
   */

  public Meeting(User organizer, CommonTypes.Day startDay, CommonTypes.Day endDay,
                  LocalTime startTime, String description, CommonTypes.Recurrence recurrence,
                  LocalTime endTime, CommonTypes.MeetingType type, 
                  CommonTypes.MeetingStatus status) {
    this.organizer = organizer;
    this.type = type;
    this.description = description;
    this.startDay = startDay;
    this.endDay = endDay;
    this.startTime = startTime;
    this.endTime = endTime;
    this.recurrence = recurrence;
    this.status = status;
  }
}

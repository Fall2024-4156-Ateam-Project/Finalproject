package dev.teamproject.meeting;

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
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.sql.Timestamp;

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
  private Timestamp startTime;

  @Column(name = "end_time")
  @NotNull(message = "End time is required")
  private Timestamp endTime;

  @Enumerated(EnumType.STRING)
  @Column(name = "recurrence")
  private CommonTypes.Recurrence recurrence;

  @Column(name = "created_at")
  private Timestamp createdAt;

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

  public void setType(CommonTypes.MeetingType type) {
    this.type = type;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public Timestamp getStartTime() {
    return startTime;
  }

  public void setStartTime(Timestamp startTime) {
    this.startTime = startTime;
  }

  public Timestamp getEndTime() {
    return endTime;
  }

  public void setEndTime(Timestamp endTime) {
    this.endTime = endTime;
  }

  public CommonTypes.Recurrence getRecurrence() {
    return recurrence;
  }

  public void setRecurrence(CommonTypes.Recurrence recurrence) {
    this.recurrence = recurrence;
  }

  public Timestamp getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(Timestamp createdAt) {
    this.createdAt = createdAt;
  }

  public Integer getInviteParticipant() {
    return inviteParticipant;
  }

  public void setInviteParticipant(Integer inviteParticipant) {
    this.inviteParticipant = inviteParticipant;
  }

  public Integer getAcceptParticipant() {
    return acceptParticipant;
  }

  public void setAcceptParticipant(Integer acceptParticipant) {
    this.acceptParticipant = acceptParticipant;
  }

  public CommonTypes.MeetingStatus getStatus() {
    return status;
  }

  public void setStatus(CommonTypes.MeetingStatus status) {
    this.status = status;
  }
}

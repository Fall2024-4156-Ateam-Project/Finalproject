package dev.teamproject.meeting;

import java.sql.Timestamp;
import jakarta.validation.constraints.NotNull;
public class MeetingDTO {

  // Only required for delete operation
  private Integer meetingId;

  // Required for save operation
  @NotNull(message = "Organizer ID is required for saving the meeting")
  private Integer organizerId;

  private String type;
  private String description;
  private String status;
  private String recurrence;
  private Timestamp startTime;
  private Timestamp endTime;

  // Getters and Setters
  public Integer getMeetingId() {
    return meetingId;
  }

  public void setMeetingId(Integer meetingId) {
    this.meetingId = meetingId;
  }

  public Integer getOrganizerId() {
    return organizerId;
  }

  public void setOrganizerId(Integer organizerId) {
    this.organizerId = organizerId;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
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

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public String getRecurrence() {
    return recurrence;
  }

  public void setRecurrence(String recurrence) {
    this.recurrence = recurrence;
  }
}
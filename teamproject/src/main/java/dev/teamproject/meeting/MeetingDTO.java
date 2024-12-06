package dev.teamproject.meeting;

import dev.teamproject.common.CommonTypes;
import dev.teamproject.common.CommonTypes.Day;
import jakarta.validation.constraints.NotNull;
import java.time.LocalTime;

/**
 * Data Transfer Object (DTO) for handling Meeting-related operations.
 * Encapsulates the necessary details for creating, updating,
 * retrieving, or deleting a meeting.
 */
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
  private LocalTime startTime;
  private LocalTime endTime;
  private CommonTypes.Day startDay;
  private CommonTypes.Day endDay;
  private String participantEmail;

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

  public CommonTypes.Day getStartDay() {
    return startDay;
  }

  public void setStartDay(CommonTypes.Day startDay) {
    this.startDay = startDay;
  }

  public CommonTypes.Day getEndDay() {
    return endDay;
  }

  public void setEndDay(CommonTypes.Day endDay) {
    this.endDay = endDay;
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

  public String getParticipantEmail() {
    return participantEmail;
  }

  public void setParticipantEmail(String participantEmail) {
    this.participantEmail = participantEmail;
  }
}
package dev.teamproject.meeting;

import dev.teamproject.common.CommonTypes;
import dev.teamproject.user.User;

import java.sql.Timestamp;

public class MeetingDTO {

  private int mid;
  private User organizer;
  private CommonTypes.MeetingType type;
  private String description;
  private Timestamp startTime;
  private Timestamp endTime;
  private CommonTypes.Recurrence recurrence;
  private Timestamp createdAt;
  private Integer inviteParticipant;
  private Integer acceptParticipant;
  private CommonTypes.MeetingStatus status;

  // Constructor
  public MeetingDTO(int mid, User organizer, CommonTypes.MeetingType type, String description,
                    Timestamp startTime, Timestamp endTime, CommonTypes.Recurrence recurrence,
                    Timestamp createdAt, Integer inviteParticipant, Integer acceptParticipant,
                    CommonTypes.MeetingStatus status) {
    this.mid = mid;
    this.organizer = organizer;
    this.type = type;
    this.description = description;
    this.startTime = startTime;
    this.endTime = endTime;
    this.recurrence = recurrence;
    this.createdAt = createdAt;
    this.inviteParticipant = inviteParticipant;
    this.acceptParticipant = acceptParticipant;
    this.status = status;
  }
  // Constructor
  public MeetingDTO() {

  }

  // Getters
  public int getMid() {
    return mid;
  }

  public User getOrganizer() {
    return organizer;
  }

  public CommonTypes.MeetingType getType() {
    return type;
  }

  public String getDescription() {
    return description;
  }

  public Timestamp getStartTime() {
    return startTime;
  }

  public Timestamp getEndTime() {
    return endTime;
  }

  public CommonTypes.Recurrence getRecurrence() {
    return recurrence;
  }

  public Timestamp getCreatedAt() {
    return createdAt;
  }

  public Integer getInviteParticipant() {
    return inviteParticipant;
  }

  public Integer getAcceptParticipant() {
    return acceptParticipant;
  }

  public CommonTypes.MeetingStatus getStatus() {
    return status;
  }

  // Setters (optional, depending on your use case)
  public void setMid(int mid) {
    this.mid = mid;
  }

  public void setOrganizer(User organizer) {
    this.organizer = organizer;
  }

  public void setType(CommonTypes.MeetingType type) {
    this.type = type;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public void setStartTime(Timestamp startTime) {
    this.startTime = startTime;
  }

  public void setEndTime(Timestamp endTime) {
    this.endTime = endTime;
  }

  public void setRecurrence(CommonTypes.Recurrence recurrence) {
    this.recurrence = recurrence;
  }

  public void setCreatedAt(Timestamp createdAt) {
    this.createdAt = createdAt;
  }

  public void setInviteParticipant(Integer inviteParticipant) {
    this.inviteParticipant = inviteParticipant;
  }

  public void setAcceptParticipant(Integer acceptParticipant) {
    this.acceptParticipant = acceptParticipant;
  }

  public void setStatus(CommonTypes.MeetingStatus status) {
    this.status = status;
  }
}

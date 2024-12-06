package dev.teamproject.user.DTOs;

import dev.teamproject.meeting.MeetingDTO;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.sql.Timestamp;
import java.util.List;


/**
 * DTO (Data Transfer Object) for creating a new user.
 * This class is used to capture the required and optional information
 * for creating a user, including name, email, and optional meetings.
 * It includes validation annotations for the required fields.
 */
public class UserCreationRequestDTO {

  /**
   * Optional.
   */
  private Integer uid;

  @NotBlank(message = "Name is required.")
  @Size(min = 2, max = 100, message = "Name must be between 2 and 100 characters.")
  private String name;


  @NotBlank(message = "Email is required.")
  @Email(message = "Email should be valid.")
  @Size(max = 100, message = "Email must not exceed 100 characters.")
  private String email;

  // Optional fields for tracking creation and update timestamps
  private Timestamp createdAt;
  private Timestamp updatedAt;

  /**
   * Optional.
   */
  private List<MeetingDTO> meetings;

  public Integer getUid() {
    return uid;
  }

  public void setUid(Integer uid) {
    this.uid = uid;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }


  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public Timestamp getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(Timestamp createdAt) {
    this.createdAt = createdAt;
  }

  public Timestamp getUpdatedAt() {
    return updatedAt;
  }

  public void setUpdatedAt(Timestamp updatedAt) {
    this.updatedAt = updatedAt;
  }

  public List<MeetingDTO> getMeetings() {
    return meetings;
  }

  public void setMeetings(List<MeetingDTO> meetings) {
    this.meetings = meetings;
  }
}
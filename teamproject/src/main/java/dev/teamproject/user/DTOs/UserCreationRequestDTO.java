package dev.teamproject.user.DTOs;

import dev.teamproject.meeting.MeetingDTO;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.sql.Timestamp;
import java.util.List;

public class UserCreationRequestDTO {

  /**
   * Optional
   */
  private Integer uid;

  @NotBlank(message = "Name is required.")
  @Size(min = 2, max = 100, message = "Name must be between 2 and 100 characters.")
  private String name;

  @NotBlank(message = "Password is required.")
  @Size(min = 8, max = 20, message = "Password must be between 8 and 20 characters.")
  // Uncomment and customize if you want to enforce a more complex password policy
  // @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$", message = "Password must contain at least one letter and one number.")
  private String password;

  @NotBlank(message = "Email is required.")
  @Email(message = "Email should be valid.")
  @Size(max = 100, message = "Email must not exceed 100 characters.")
  private String email;

  // Optional fields for tracking creation and update timestamps
  private Timestamp createdAt;
  private Timestamp updatedAt;

  /**
   * Optional
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

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
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
package dev.teamproject.user.dto;

import dev.teamproject.user.User;
import java.sql.Timestamp;

/**
 * Data Transfer Object (DTO) for the successful user response.
 * This class contains the user's UID, name, email, and timestamps
 * for account creation and update, which are used to return user information
 * in a successful response.
 */
public class UserSuccessResponseDto {

  private Integer uid;
  private String name;
  private String email;
  private Timestamp createdAt;
  private Timestamp updatedAt;


  public Integer getUid() {
    return uid;
  }

  public String getName() {
    return name;
  }

  public String getEmail() {
    return email;
  }

  public Timestamp getCreatedAt() {
    return createdAt;
  }

  public Timestamp getUpdatedAt() {
    return updatedAt;
  }

  public void setUid(Integer uid) {
    this.uid = uid;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public void setCreatedAt(Timestamp createdAt) {
    this.createdAt = createdAt;
  }

  public void setUpdatedAt(Timestamp updatedAt) {
    this.updatedAt = updatedAt;
  }

  /**
   * Sets the response fields using the provided User object.
   * This method maps the fields of the given User object to the corresponding
   * response fields such as UID, name, email, and creation date.
   */
  public void setUserResponseFromUser(User user) {
    setUid(user.getUid());
    setName(user.getName());
    setEmail(user.getEmail());
    setCreatedAt(user.getCreatedAt());
    //  response.setUpdatedAt(user.getUpdatedAt());
  }
}

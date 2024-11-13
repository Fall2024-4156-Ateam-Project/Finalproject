package dev.teamproject.user.DTOs;

import dev.teamproject.user.User;
import java.sql.Timestamp;

public class UserSuccessResponseDTO {

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


  public void setUserResponseFromUser(User user) {
    setUid(user.getUid());
    setName(user.getName());
    setEmail(user.getEmail());
    setCreatedAt(user.getCreatedAt());
//    response.setUpdatedAt(user.getUpdatedAt());
  }
}

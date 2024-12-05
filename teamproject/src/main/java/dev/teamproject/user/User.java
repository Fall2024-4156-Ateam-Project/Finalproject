package dev.teamproject.user;

import dev.teamproject.meeting.Meeting;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;

/**
 * Represents a user in the system. This entity includes information like the user's name, email,
 * and the list of meetings they have organized.
 */

@Entity
@Table(name = "User")
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "UID", nullable = false)
  private int uid;

  @Column(name = "name", length = 100)
  private String name;

  @Column(name = "email", length = 100, unique = true)
  private String email;

  @Column(name = "password_hash", length = 255)
  private String password_hash;

  @Column(name = "createdAt")
  private Timestamp createdAt;
  @Column(name = "updateAt")
  private Timestamp updatedAt;

  @OneToMany(mappedBy = "organizer")
  private List<Meeting> meetings;


  @PrePersist
  protected void onCreate() {
    createdAt = new Timestamp(System.currentTimeMillis());
  }

  @PreUpdate
  protected void onUpdate() {
    updatedAt = new Timestamp(System.currentTimeMillis());
  }

  // Default constructor
  public User() {
  }

  // Parameterized constructor
  public User(String name, String email) {
    this.name = name;
    this.email = email;
  }

  public int getUid() {
    return uid;
  }
  
  public void setUid(int uid) {
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

  public void setPassword_hash(String password_hash) {
    this.password_hash = password_hash;
  }

  public String getPassword_hash() {
    return password_hash;
  }

  public Timestamp getCreatedAt() {
    return createdAt;
  }

  public Timestamp getUpdatedAt() {
    return updatedAt;
  }

  public void setCreatedAt(Timestamp createdAt) {
    this.createdAt = createdAt;
  }

  public void setUpdatedAt(Timestamp updatedAt) {
    this.updatedAt = updatedAt;
  }

  @Override
  public int hashCode() {
    return Objects.hash(uid); // Use uid as a unique identifier
  }

  @Override
  public String toString() {    
    return "User{" 
        + "uid=" + uid 
        + ", name='" + name + '\'' 
        + ", email='" + email + '\'' 
        + ", createdAt=" + createdAt 
        + ", updatedAt=" + updatedAt 
        + '}';
  }
}
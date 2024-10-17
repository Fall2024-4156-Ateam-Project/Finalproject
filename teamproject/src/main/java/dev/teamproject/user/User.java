package dev.teamproject.user;

import dev.teamproject.meeting.Meeting;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.List;
import java.util.Objects;

/**
 * Represents a user in the system. This entity includes information like
 * the user's name, email, and the list of meetings they have organized.
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
  
  @Column(name = "email", length = 100)
  private String email;
  
  @OneToMany(mappedBy = "organizer")
  private List<Meeting> meetings;
  
  
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
  
  //    public void setUid(int uid) {
  //        this.uid = uid;
  //    }
  
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
  
  @Override
  public int hashCode() {
    return Objects.hash(uid); // Use uid as a unique identifier
  }
}
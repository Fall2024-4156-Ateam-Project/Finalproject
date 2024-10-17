package dev.teamproject.participant;

import dev.teamproject.common.CommonTypes;
import dev.teamproject.meeting.Meeting;
import dev.teamproject.user.User;
import jakarta.persistence.*;
import java.sql.Timestamp;

/**
 * This entity class represents a database table named Participant.
 * It defines the structure, relationships, and behavior of
 * participant records within the application
 */
 
@Entity
@Table(name = "Participant")
public class Participant {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "PID")
  private int pid;

  @ManyToOne
  @JoinColumn(name = "MID", nullable = false)
  private Meeting meeting;

  @ManyToOne
  @JoinColumn(name = "UID", nullable = false)
  private User user;

  @Enumerated(EnumType.STRING)
  @Column(name = "role")
  private CommonTypes.Role role;

  @Column(name = "join_at")
  private Timestamp joinAt;

  @Enumerated(EnumType.STRING)
  @Column(name = "status")
  private CommonTypes.ParticipantStatus status;

  // constructor
  public Participant() {
  }

  /**
   * This constructor creates a new participant instance by initializing
   * meeting, user, role, and status attributes with the specified values.
   *
   * @param meeting the meeting associated with this participant
   * @param user the user participating in the meeting
   * @param role the role of the participant in the meeting
   * @param status the current status of the participant
   */
  
  public Participant(Meeting meeting, User user, CommonTypes.Role role,
                     CommonTypes.ParticipantStatus status) {
    this.meeting = meeting;
    this.user = user;
    this.role = role;
    Timestamp timestamp = new Timestamp(System.currentTimeMillis());
    this.joinAt = timestamp;
    this.status = status;
  }

  // Getters and Setters
  public int getPid() {
    return pid;
  }

  public void setPid(int pid) {
    this.pid = pid;
  }

  public Meeting getMeeting() {
    return meeting;
  }

  public void setMeeting(Meeting meeting) {
    this.meeting = meeting;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public CommonTypes.Role getRole() {
    return role;
  }

  public void setRole(CommonTypes.Role role) {
    this.role = role;
  }

  public Timestamp getJoinAt() {
    return joinAt;
  }

  public void setJoinAt(Timestamp joinAt) {
    this.joinAt = joinAt;
  }

  public CommonTypes.ParticipantStatus getStatus() {
    return status;
  }

  public void setStatus(CommonTypes.ParticipantStatus status) {
    this.status = status;
  }

}
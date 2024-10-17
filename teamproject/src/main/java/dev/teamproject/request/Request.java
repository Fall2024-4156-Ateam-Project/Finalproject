package dev.teamproject.request;

import dev.teamproject.common.CommonTypes;
import dev.teamproject.timeslot.TimeSlot;
import dev.teamproject.user.User;
import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "Request")
@IdClass(RequestId.class)
public class Request {
  
  @Id
  @ManyToOne
  @JoinColumn(name = "requesterId", nullable = false)
  private User user;
  
  @Id
  @ManyToOne
  @JoinColumn(name = "tid", nullable = false)
  private TimeSlot timeSlot;
  
  @Column(name = "description", nullable = true)
  private String description;
  
  @Column(name = "status", nullable = false)
  @Enumerated(EnumType.STRING)
  private CommonTypes.RequestStatus status;
  
  
  // No-argument constructor
  public Request() {
  }
  
  public Request(User user, TimeSlot ts, String description, CommonTypes.RequestStatus status) {
    this.user = user;
    this.timeSlot = ts;
    this.description = description;
    this.status = status;
  }
  
  public void setStatus(CommonTypes.RequestStatus status) {
    this.status = status;
  }
  
  public void setDescription(String description) {
    this.description = description;
  }
  
  public User getUser() {
    return user;
  }
  
  public TimeSlot getTimeSlot() {
    return timeSlot;
  }
  
  public void setUser(User user) {
    this.user = user;
  }
  
  public void setTimeSlot(TimeSlot ts) {
    this.timeSlot = ts;
  }
  
  public String getDescription() {
    return description;
  }
  
  public CommonTypes.RequestStatus getStatus() {
    return status;
  }
  
  
  // toString
  @Override
  public String toString() {
    return "Request{"
      + "tid=" + timeSlot.getTid()
      + ", requester=" + user.getUid()
      + ", description=" + description
      + ", status=" + status
      + '}';
  }
  
  @Override
  public int hashCode() {
    return Objects.hash(user != null ? user.getUid() : null, timeSlot != null ? timeSlot.getTid() : null);
  }
  
  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (obj == null || getClass() != obj.getClass()) return false;
    
    Request other = (Request) obj;
    return (user != null ? user.getUid() == other.user.getUid() : other.user == null) &&
      (timeSlot != null ? timeSlot.getTid() == other.timeSlot.getTid() : other.timeSlot == null);
  }
  
  
}

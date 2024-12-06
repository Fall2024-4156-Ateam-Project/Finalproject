package dev.teamproject.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import dev.teamproject.common.CommonTypes;
import dev.teamproject.timeslot.TimeSlot;
import dev.teamproject.user.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.util.Objects;

/**
 * The Request entity represents a user's request for a particular time slot.
 * It contains the requester, the time slot, a description of the request, and its current status.
 */

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
  @JsonIgnoreProperties("requests")
  private TimeSlot timeSlot;
  
  @Column(name = "description", nullable = true)
  private String description;
  
  @Column(name = "status", nullable = false)
  @Enumerated(EnumType.STRING)
  private CommonTypes.RequestStatus status;
  
  
  // No-argument constructor
  public Request() {
  }

  /**
   * Constructs a new Request with the provided user, time slot, description, and status.
   *
   * @param user        the user making the request.
   * @param ts          the time slot associated with the request.
   * @param description a description of the request.
   * @param status      the current status of the request.
   */

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
    return Objects.hash(
            user != null ? user.getUid() : null,
            timeSlot != null ? timeSlot.getTid() : null
    );
  }
  
  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null || getClass() != obj.getClass()) {
      return false;
    }
    
    Request other = (Request) obj;
    return (user != null
            ? user.getUid() == other.user.getUid()
            : other.user == null)
            && (timeSlot != null
            ? timeSlot.getTid() == other.timeSlot.getTid()
            : other.timeSlot == null);
  }
  
  
}

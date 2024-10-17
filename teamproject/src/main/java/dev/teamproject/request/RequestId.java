package dev.teamproject.request;

import dev.teamproject.timeslot.TimeSlot;
import dev.teamproject.user.User;

import java.io.Serializable;
import java.util.Objects;

public class RequestId implements Serializable {

  private TimeSlot timeSlot;
  private User user;
  
  public RequestId() {
  }
  
  public RequestId(TimeSlot ts, User user) {
    this.timeSlot = ts;
    this.user = user;
  }
  
  // Getters and setters
  public TimeSlot getTimeSlot() {
    return timeSlot;
  }
  
  public void setTimeSlot(TimeSlot ts) {
    this.timeSlot = ts;
  }
  
  public User getUser() {
    return user;
  }
  
  public void setUser(User user) {
    this.user = user;
  }
  
  @Override
  public int hashCode() {
    return Objects.hash(timeSlot, user);
  }
  
  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof RequestId)) return false;
    RequestId that = (RequestId) o;
    return Objects.equals(timeSlot, that.timeSlot) && (user.getUid() == that.user.getUid());
  }
}

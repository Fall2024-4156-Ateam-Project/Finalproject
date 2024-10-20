package dev.teamproject.meeting;

import dev.teamproject.common.CommonTypes;
import dev.teamproject.user.User;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * This service handles the business logic related to
 * meeting entities.
*/
@Service
public class MeetingService {
  private final MeetingRepo meetingRepo;
  
  @Autowired
  public MeetingService(MeetingRepo meetingRepo) {
    this.meetingRepo = meetingRepo;
  }
  
  public Meeting findById(int mid) {
    return meetingRepo.findById(mid).get();
  }
  //TODO: more business logics

  public List<Meeting> findAll() {
    return meetingRepo.findAllByOrderByMidDesc();
  }

  public List<Meeting> findByOrganizer(User organizer) {
    return meetingRepo.findByOrganizer(organizer);
  }

  public List<Meeting> findByRecurrence(CommonTypes.Recurrence recurrence) {
    return meetingRepo.findByRecurrence(recurrence);
  }

  public List<Meeting> findByStatus(CommonTypes.MeetingStatus status) {
    return meetingRepo.findByStatus(status);
  }

  public List<Meeting> findByType(CommonTypes.MeetingType type) {
    return meetingRepo.findByType(type);
  }

  public void delete(Meeting meeting) {
    meetingRepo.delete(meeting);
  }
  public void deleteMeeting(int mid) {
    if (!this.meetingRepo.existsById(mid)) {
      throw new RuntimeException("Meeting not found with id: " + mid);
    }
    meetingRepo.deleteById(mid);
  }
  public void save(Meeting meeting) {
    meetingRepo.save(meeting);
  }

}

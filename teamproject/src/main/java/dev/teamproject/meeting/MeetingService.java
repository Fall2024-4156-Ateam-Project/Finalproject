package dev.teamproject.meeting;

import dev.teamproject.common.CommonTypes;
import dev.teamproject.exceptionHandler.IllegalArgumentException;
import dev.teamproject.user.User;
import dev.teamproject.user.UserService;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

/** This service handles the business logic related to meeting entities. */
@Service
public class MeetingService {
  private final MeetingRepo meetingRepo;
  private final UserService userService; // for validation
  private final Object lock = new Object();

  @Autowired
  public MeetingService(MeetingRepo meetingRepo, UserService userService) {
    this.meetingRepo = meetingRepo;
    this.userService = userService;
  }

  @Transactional(readOnly = true)
  public Meeting findById(int mid) {
    return meetingRepo.findById(mid).get();
  }

  // TODO: more business logics
  @Transactional(readOnly = true)
  public List<Meeting> findAll() {
    return meetingRepo.findAllByOrderByMidDesc();
  }

  @Transactional(readOnly = true)
  public List<Meeting> findByOrganizer(User organizer) {
    return meetingRepo.findByOrganizer(organizer);
  }

  @Transactional(readOnly = true)
  public List<Meeting> findByRecurrence(CommonTypes.Recurrence recurrence) {
    return meetingRepo.findByRecurrence(recurrence);
  }

  @Transactional(readOnly = true)
  public List<Meeting> findByStatus(CommonTypes.MeetingStatus status) {
    return meetingRepo.findByStatus(status);
  }

  @Transactional(readOnly = true)
  public List<Meeting> findByType(CommonTypes.MeetingType type) {
    return meetingRepo.findByType(type);
  }


  /**
   * Delete a meeting by requesting a meeting id.
   */
  @Transactional
  public void deleteMeeting(int mid) {
    if (!meetingRepo.existsById(mid)) {
      throw new RuntimeException("Meeting not found with id: " + mid);
    }
    meetingRepo.deleteById(mid);

  }

  @Transactional
  @Validated
  public void save(MeetingDTO meetingDTO) {
    if (meetingDTO.getOrganizerId() == null
            || meetingDTO.getStartTime() == null
            || meetingDTO.getEndTime() == null
            || meetingDTO.getType() == null) {
      throw new IllegalArgumentException("Missing required fields for saving the meeting");
    }

    synchronized (lock) { // Protect validation and save
      User organizer = userService.findById(meetingDTO.getOrganizerId());

      // Set the meeting
      Meeting meeting = new Meeting();
      meeting.setOrganizer(organizer);
      try {
        meeting.setType(CommonTypes.MeetingType.valueOf(meetingDTO.getType()));
      } catch (IllegalArgumentException e) {
        throw new IllegalArgumentException("Invalid meeting type: " + meetingDTO.getType());
      }

      meeting.setDescription(meetingDTO.getDescription());
      meeting.setStartTime(meetingDTO.getStartTime());
      meeting.setEndTime(meetingDTO.getEndTime());

      meetingRepo.save(meeting);
    }
  }
}

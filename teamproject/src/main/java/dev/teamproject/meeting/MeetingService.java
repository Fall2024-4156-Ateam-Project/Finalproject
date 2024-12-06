package dev.teamproject.meeting;

import dev.teamproject.common.CommonTypes;
import dev.teamproject.exceptionhandler.IllegalArgumentException;
import dev.teamproject.participant.Participant;
import dev.teamproject.participant.ParticipantService;
import dev.teamproject.user.User;
import dev.teamproject.user.UserService;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

/** This service handles the business logic related to meeting entities. */
@Service
public class MeetingService {
  private final MeetingRepo meetingRepo;
  private final UserService userService; // for validation
  private final ParticipantService participantService;
  private final Object lock = new Object();

  /** Constructor for MeetingService class. */
  @Autowired
  public MeetingService(MeetingRepo meetingRepo, UserService userService, 
      ParticipantService participantService) {
    this.meetingRepo = meetingRepo;
    this.userService = userService;
    this.participantService = participantService;
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

  /** Find meeting with the given user email. */
  @Transactional(readOnly = true)
  public List<Meeting> findByEmail(String email) {

    List<User> users = userService.findByEmail(email);
    List<Meeting> meetings = new ArrayList<>();
    for (User user : users) {
      meetings.addAll(meetingRepo.findByOrganizer(user));
    }
    return meetings;
  }


  /**
   * Delete a meeting by requesting a meeting id.
   */
  @Transactional
  public void deleteMeeting(int mid) {
    if (!meetingRepo.existsById(mid)) {
      throw new RuntimeException("Meeting not found with id: " + mid);
    }
    List<Participant> participants = participantService.findByMeeting(meetingRepo.findByMid(mid));
    for (Participant participant : participants) {
      participantService.deleteParticipant(participant.getPid());
    }
    meetingRepo.deleteById(mid);

  }

  /** Save the meeting.  */
  @Transactional
  @Validated
  public void save(MeetingDto meetingDto) {
    if (meetingDto.getOrganizerId() == null
            || meetingDto.getStartTime() == null
            || meetingDto.getEndTime() == null
            || meetingDto.getStartDay() == null
            || meetingDto.getEndDay() == null
            || meetingDto.getType() == null) {
      throw new IllegalArgumentException("Missing required fields for saving the meeting");
    }

    synchronized (lock) { // Protect validation and save
      User organizer = userService.findById(meetingDto.getOrganizerId());
      if (organizer == null) {
        throw new IllegalArgumentException("Organizer does not exist.");
      }
      List<User> participants = userService.findByEmail(meetingDto.getParticipantEmail());
      if (participants.isEmpty()) {
        throw new IllegalArgumentException("Participant does not exist.");
      }

      // Set the meeting
      Meeting meeting = new Meeting();
      meeting.setOrganizer(organizer);
      try {
        meeting.setType(CommonTypes.MeetingType.valueOf(meetingDto.getType()));
      } catch (IllegalArgumentException e) {
        throw new IllegalArgumentException("Invalid meeting type: " + meetingDto.getType());
      }
      try {
        meeting.setStatus(CommonTypes.MeetingStatus.valueOf(meetingDto.getStatus()));
      } catch (IllegalArgumentException e) {
        throw new IllegalArgumentException("Invalid meeting status: " + meetingDto.getStatus());
      }
      try {
        meeting.setRecurrence(CommonTypes.Recurrence.valueOf(meetingDto.getRecurrence()));
      } catch (IllegalArgumentException e) {
        throw new IllegalArgumentException("Invalid meeting recurrence: " 
        + meetingDto.getRecurrence());
      }

      meeting.setDescription(meetingDto.getDescription());
      meeting.setStartTime(meetingDto.getStartTime());
      meeting.setEndTime(meetingDto.getEndTime());
      meeting.setStartDay(meetingDto.getStartDay());
      meeting.setEndDay(meetingDto.getEndDay());
      meeting.setCreatedAt(LocalTime.now());

      meetingRepo.save(meeting);

      User participant = participants.get(0);

      participantService.save(new Participant(meeting, organizer, CommonTypes.Role.organizer, 
          CommonTypes.ParticipantStatus.accept));
      participantService.save(new Participant(meeting, participant, CommonTypes.Role.participant, 
          CommonTypes.ParticipantStatus.accept));

    }
  }
}

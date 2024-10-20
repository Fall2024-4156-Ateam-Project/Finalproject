package dev.teamproject.participant;

import dev.teamproject.common.CommonTypes;
import dev.teamproject.meeting.Meeting;
import dev.teamproject.user.User;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service class for managing Participant entities.
 * This class provides business logic and operations for participants,
 * utilizing the ParticipantRepo for data access. It handles the
 * retrieval, creation, updating, and deletion of participants within the
 * application.
 */
@Service
public class ParticipantService {
  private final ParticipantRepo participantRepo;
  
  @Autowired
  public ParticipantService(ParticipantRepo participantRepo) {
    this.participantRepo = participantRepo;
  }
  
  public Participant findById(int pid) {
    return this.participantRepo.findById(pid);
  }
  //TODO: more business logics
  
  public List<Participant> findAll() {
    return this.participantRepo.findAllByOrderByPidDesc();
  }
  
  public List<Participant> findByMeeting(Meeting meeting) {
    return this.participantRepo.findByMeeting(meeting);
  }
  
  public List<Participant> findByUser(User user) {
    return this.participantRepo.findByUser(user);
  }
  
  public List<Participant> findByStatus(CommonTypes.ParticipantStatus status) {
    return this.participantRepo.findByStatus(status);
  }
  
  public List<Participant> findByRole(CommonTypes.Role role) {
    return this.participantRepo.findByRole(role);
  }
  
  public void save(Participant participant) {
    this.participantRepo.save(participant);
  }

  /**
   * Delete a participant by requesting a participant id.
   */

  public void deleteParticipant(int pid) {
    if (!participantRepo.existsById(pid)) {
      throw new RuntimeException("Participant not found with id: " + pid);
    }
    participantRepo.deleteById(pid);
  }
  
}


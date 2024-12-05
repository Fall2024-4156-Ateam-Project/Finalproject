package dev.teamproject.participant;

import dev.teamproject.common.CommonTypes;
import dev.teamproject.meeting.Meeting;
import dev.teamproject.user.User;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
  private final Object lock = new Object(); // Lock object for critical sections

  @Autowired
  public ParticipantService(ParticipantRepo participantRepo) {
    this.participantRepo = participantRepo;
  }

  @Transactional(readOnly = true)
  public Participant findById(int pid) {
    return this.participantRepo.findById(pid);
  }

  @Transactional(readOnly = true)
  public List<Participant> findAll() {
    return this.participantRepo.findAllByOrderByPidDesc();
  }

  @Transactional(readOnly = true)
  public List<Participant> findByMeeting(Meeting meeting) {
    return this.participantRepo.findByMeeting(meeting);
  }

  @Transactional(readOnly = true)
  public List<Participant> findByUser(User user) {
    return this.participantRepo.findByUser(user);
  }

  @Transactional(readOnly = true)
  public List<Participant> findByStatus(CommonTypes.ParticipantStatus status) {
    return this.participantRepo.findByStatus(status);
  }

  @Transactional(readOnly = true)
  public List<Participant> findByRole(CommonTypes.Role role) {
    return this.participantRepo.findByRole(role);
  }

  /**
  * Saves a {@link Participant} entity to the repository in a transactional context.
  * This method ensures thread-safety by synchronizing on a shared lock object,
  * protecting the critical section during the save operation.
  */
  @Transactional
  public void save(Participant participant) {
    synchronized (lock) { // Protect critical section
      this.participantRepo.save(participant);
    }
  }

  /**
   * Delete a participant by requesting a participant id.
   */
  @Transactional
  public void deleteParticipant(int pid) {
    if (!participantRepo.existsById(pid)) {
      throw new RuntimeException("Participant not found with id: " + pid);
    }
    participantRepo.deleteById(pid);
  }
}

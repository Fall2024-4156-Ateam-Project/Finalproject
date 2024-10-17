package dev.teamproject.participant;

import dev.teamproject.common.CommonTypes;
import dev.teamproject.meeting.Meeting;
import dev.teamproject.user.User;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for managing Participant entities.
 * This interface extends JpaRepository, providing methods for CRUD operations
 * and custom query methods for managing participants in the system.
 * Methods include finding participants by their role, status, associated meeting,
 * and user, as well as retrieving participants in descending order by participant ID.
 */

@Repository
public interface ParticipantRepo extends JpaRepository<Participant, Integer> {
  
  Participant findById(int pid);
  
  List<Participant> findByRole(CommonTypes.Role role);
  
  List<Participant> findByStatus(CommonTypes.ParticipantStatus status);
  
  List<Participant> findByMeeting(Meeting meeting);
  
  List<Participant> findByUser(User user);
  
  List<Participant> findAllByOrderByPidDesc();
}

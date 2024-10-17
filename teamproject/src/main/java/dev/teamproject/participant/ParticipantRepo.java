package dev.teamproject.participant;

import dev.teamproject.common.CommonTypes;
import dev.teamproject.meeting.Meeting;
import dev.teamproject.user.User;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParticipantRepo extends JpaRepository<Participant, Integer> {
  
  Participant findById(int pid);
  
  List<Participant> findByRole(CommonTypes.Role role);
  
  List<Participant> findByStatus(CommonTypes.ParticipantStatus status);
  
  List<Participant> findByMeeting(Meeting meeting);
  
  List<Participant> findByUser(User user);
  
  List<Participant> findAllByOrderByPidDesc();
}

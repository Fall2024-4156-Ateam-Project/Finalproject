package dev.teamproject.participant;
import dev.teamproject.user.User;
import dev.teamproject.meeting.Meeting;
import dev.teamproject.common.commonTypes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ParticipantRepo extends JpaRepository<Participant, Integer> {
    Participant findById(int PID);
    List<Participant> findByRole(commonTypes.Role role);
    List<Participant> findByStatus(commonTypes.ParticipantStatus status);
    List<Participant> findByMeeting(Meeting meeting);
    List<Participant> findByUser(User user);
    List<Participant> findAllByOrderByPidDesc();
}

package dev.teamproject.meeting;
import dev.teamproject.common.commonTypes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import dev.teamproject.user.User;
import java.util.List;
@Repository
public interface MeetingRepo extends JpaRepository<Meeting, Integer> {
    List<Meeting> findByRecurrence(commonTypes.Recurrence recurrence);

    Meeting findByMid(int mid);
    List<Meeting> findAllByOrderByMidDesc();

    List<Meeting> findByOrganizer(User organizer);

    List<Meeting> findByStatus(commonTypes.MeetingStatus status);

    List<Meeting> findByType(commonTypes.MeetingType type);
}

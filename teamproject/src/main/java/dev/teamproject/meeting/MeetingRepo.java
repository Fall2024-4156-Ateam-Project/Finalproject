package dev.teamproject.meeting;

import dev.teamproject.common.CommonTypes;
import dev.teamproject.user.User;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * This is a Repository interface used for managing {@link Meeting} entities.
 * This interface extends {@link JpaRepository} to provide CRUD operations
 * and additional query methods for meeting records.
 */

@Repository
public interface MeetingRepo extends JpaRepository<Meeting, Integer> {
  List<Meeting> findByRecurrence(CommonTypes.Recurrence recurrence);

  Meeting findByMid(int mid);
  
  List<Meeting> findAllByOrderByMidDesc();

  List<Meeting> findByOrganizer(User organizer);

  List<Meeting> findByStatus(CommonTypes.MeetingStatus status);

  List<Meeting> findByType(CommonTypes.MeetingType type);
}

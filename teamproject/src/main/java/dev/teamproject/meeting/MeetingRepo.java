package dev.teamproject.meeting;

import dev.teamproject.common.CommonTypes;
import dev.teamproject.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * This is a Repository interface used for managing {@link Meeting} entities.
 * This interface extends {@link JpaRepository} to provide CRUD operations
 * and additional query methods for meeting records.
 */
@Repository
public interface MeetingRepo extends JpaRepository<Meeting, Integer> {

  // Query methods to find meetings by recurrence
  List<Meeting> findByRecurrence(CommonTypes.Recurrence recurrence);

  // Find meeting by ID
  Meeting findByMid(int mid);

  // Get all meetings ordered by mid in descending order
  List<Meeting> findAllByOrderByMidDesc();

  // Find meetings organized by a specific user
  List<Meeting> findByOrganizer(User organizer);

  // Find meetings by status
  List<Meeting> findByStatus(CommonTypes.MeetingStatus status);

  // Find meetings by type
  List<Meeting> findByType(CommonTypes.MeetingType type);

}

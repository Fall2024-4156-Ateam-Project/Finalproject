package dev.teamproject.timeslot;

import dev.teamproject.common.CommonTypes;
import dev.teamproject.user.User;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for managing TimeSlot entities.
 * Extends JpaRepository to provide CRUD operations and custom query methods.
 */

@Repository
public interface TimeSlotRepo extends JpaRepository<TimeSlot, Integer> {
  List<TimeSlot> findByUser(User user);

  
  List<TimeSlot> findByStartDay(CommonTypes.Day day);
  
  List<TimeSlot> findByAvailability(CommonTypes.Availability availability);
  
  List<TimeSlot> findByUserAndStartDay(User user, CommonTypes.Day day);
}

package dev.teamproject.request;

import dev.teamproject.timeslot.TimeSlot;
import dev.teamproject.user.User;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for managing Request entities.
 * Extends JpaRepository to provide CRUD operations on the Request entity.
 * This interface handles interactions with the database.
 */

@Repository
public interface RequestRepo extends JpaRepository<Request, RequestId> {
  List<Request> findByUser(User user);
  
  List<Request> findByTimeSlot(TimeSlot ts);
}

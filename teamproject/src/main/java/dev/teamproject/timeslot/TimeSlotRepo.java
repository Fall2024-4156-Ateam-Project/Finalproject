package dev.teamproject.timeslot;

import dev.teamproject.common.CommonTypes;
import dev.teamproject.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TimeSlotRepo extends JpaRepository<TimeSlot, Integer> {
  List<TimeSlot> findByUser(User user);
  
  List<TimeSlot> findByDay(CommonTypes.Day day);
  
  List<TimeSlot> findByAvailability(CommonTypes.Availability availability);
  
  List<TimeSlot> findByUserAndDay(User user, CommonTypes.Day day);
}

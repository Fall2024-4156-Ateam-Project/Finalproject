package dev.teamproject.timeslot;

import dev.teamproject.common.CommonTypes;
import dev.teamproject.common.CommonTypes.Day;
import dev.teamproject.user.User;
import java.time.LocalTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
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


  @Query("SELECT ts FROM TimeSlot ts WHERE ts.user.uid = :userId AND "
          + "((ts.startDay < :endDay) "
          + "OR (ts.startDay = :endDay AND ts.startTime <= :endTime)) AND "
          + "((ts.endDay > :startDay) OR (ts.endDay = :startDay AND ts.endTime >= :startTime))")
  List<TimeSlot> findOverlappingTimeSlots(
      @Param("startDay") Day startDay,
      @Param("endDay") Day endDay,
      @Param("startTime") LocalTime startTime,
      @Param("endTime") LocalTime endTime,
      @Param("userId") Integer userId);
}

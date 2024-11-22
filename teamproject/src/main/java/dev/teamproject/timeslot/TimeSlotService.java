package dev.teamproject.timeslot;

import dev.teamproject.common.CommonTypes;
import dev.teamproject.user.User;
import dev.teamproject.user.UserService;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service class for managing TimeSlot entities. This class provides
 * functionality for creating, retrieving, updating, and deleting time slots.
 */

@Service
public class TimeSlotService {
  private final TimeSlotRepo timeSlotRepo;
  private final UserService userService;
  private final Object lock = new Object();


  @Autowired
  public TimeSlotService(TimeSlotRepo timeSlotRepo, UserService userService) {
    this.timeSlotRepo = timeSlotRepo;
    this.userService = userService;
  }

  /**
   * Creates a new TimeSlot. Ensures thread safety with synchronized block.
   */
  @Transactional
  public TimeSlot createTimeSlot(TimeSlot timeSlot) {
    User user = userService.findById(timeSlot.getUser().getUid());
    timeSlot.setUser(user);
    synchronized (lock) {
      return timeSlotRepo.save(timeSlot);
    }
  }

  /**
   * Retrieves a TimeSlot by its ID.
   */
  @Transactional(readOnly = true)
  public TimeSlot getTimeSlotById(int tid) {
    return timeSlotRepo.findById(tid)
            .orElseThrow(() -> new RuntimeException("TimeSlot not found with id: " + tid));
  }

  /**
   * Retrieves all TimeSlots.
   */
  @Transactional(readOnly = true)
  public List<TimeSlot> getAllTimeSlots() {
    return timeSlotRepo.findAll();
  }

  /**
   * Retrieves all TimeSlots for a specific user.
   */
  @Transactional(readOnly = true)
  public List<TimeSlot> getTimeSlotsByUser(int uid) {
    User user = userService.findById(uid);
    return timeSlotRepo.findByUser(user);
  }

  public List<TimeSlot> getTimeSlotsByUserEmail(String email) {
    List<User> users = userService.findByEmail(email);
    List<TimeSlot> timeslots = new ArrayList<>();
    for (User user : users) {
      timeslots.addAll(timeSlotRepo.findByUser(user));
    }
    return timeslots;
  }

  /**
   * Retrieves all TimeSlots for a specific day.
   */
  @Transactional(readOnly = true)
  public List<TimeSlot> getTimeSlotsByDay(CommonTypes.Day day) {
    return timeSlotRepo.findByDay(day);
  }

  /**
   * Retrieves all TimeSlots based on availability.
   */
  @Transactional(readOnly = true)
  public List<TimeSlot> getTimeSlotsByAvailability(CommonTypes.Availability availability) {
    return timeSlotRepo.findByAvailability(availability);
  }

  /**
   * Updates an existing TimeSlot. Ensures thread safety with synchronized block.
   */
  @Transactional
  public TimeSlot updateTimeSlot(int tid, TimeSlot updatedTimeSlot) {
    synchronized (lock) {
      TimeSlot existingTimeSlot = getTimeSlotById(tid);
      existingTimeSlot.setUser(updatedTimeSlot.getUser());
      existingTimeSlot.setDay(updatedTimeSlot.getDay());
      existingTimeSlot.setStartTime(updatedTimeSlot.getStartTime());
      existingTimeSlot.setEndTime(updatedTimeSlot.getEndTime());
      existingTimeSlot.setAvailability(updatedTimeSlot.getAvailability());
      return timeSlotRepo.save(existingTimeSlot);
    }
  }

  /**
   * Deletes an existing TimeSlot by ID.
   */
  @Transactional
  public void deleteTimeSlot(int tid) {
    if (!timeSlotRepo.existsById(tid)) {
      throw new RuntimeException("TimeSlot not found with id: " + tid);
    }
    timeSlotRepo.deleteById(tid);
  }
}

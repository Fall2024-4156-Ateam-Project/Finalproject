package dev.teamproject.timeslot;


import dev.teamproject.common.CommonTypes;
import dev.teamproject.common.CommonTypes.Availability;
import dev.teamproject.user.User;
import dev.teamproject.user.UserService;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service class for managing TimeSlot entities. This class provides functionality for creating,
 * retrieving, updating, and deleting time slots.
 */

@Service
public class TimeSlotService {

  private final TimeSlotRepo timeSlotRepo;
  private final UserService userService;

  private final TimeSlotHelper timeSlotHelper;

  @Autowired
  public TimeSlotService(TimeSlotRepo timeSlotRepo, UserService userService,
      TimeSlotHelper timeSlotHelper) {
    this.timeSlotRepo = timeSlotRepo;
    this.userService = userService;
    this.timeSlotHelper = timeSlotHelper;
  }

  /**
   * Creates a new TimeSlot.
   */

  public TimeSlot createTimeSlot(TimeSlot timeSlot) {
    User user = userService.findById(timeSlot.getUser().getUid());
    timeSlot.setUser(user);
    return timeSlotRepo.save(timeSlot);
  }

  /**
   * Given a new timeslot, this method update or create new timeslots **Assume non overlapping
   * timeslots** 5 outcomes: 1. expand existing single timeslot: expand left or right 2. divided
   * existing single timeslot: into 2 or 3 3. cover multiple timeslots: do override and create new 2
   * or 3 timeslots 4. trivial: create new single timeslots
   *
   * @param timeSlot
   * @return
   */
  public TimeSlot createMergeTimeSlot(TimeSlot timeSlot) {
    User user = userService.findById(timeSlot.getUser().getUid());
    List<TimeSlot> timeSlotList = this.getAllTimeSlots(); //assume non overlapping
    // time slot sort: start(weekday + time) - end(weekday + time)
    Collections.sort(timeSlotList, new TimeSlotComparator());

//    LocalTime requestStartTime = timeSlot.getStartTime();
//    LocalTime requestEndTime = timeSlot.getEndTime();
//    Availability requestavailability = timeSlot.getAvailability();

    List<TimeSlot> affectedSlots = new ArrayList<>();
    // use a stack
    for (TimeSlot ts : timeSlotList) {
      System.out.println(ts);
      // if the timeslot are overlapped
      if (timeSlotHelper.isOverlapped(ts, timeSlot)) {
        System.out.println(
            "overlap found" + ts.getStartDay().toString() + timeSlot.getStartDay().toString());
        affectedSlots.add(ts);
      }
    }
    return timeSlot;
  }

  /**
   * Retrieves a TimeSlot by its ID.
   */

  public TimeSlot getTimeSlotById(int tid) {
    return timeSlotRepo.findById(tid)
        .orElseThrow(() -> new RuntimeException("TimeSlot not found with id: " + tid));
  }

  /**
   * Retrieves all TimeSlots.
   */
  public List<TimeSlot> getAllTimeSlots() {
    return timeSlotRepo.findAll();
  }

  /**
   * Retrieves all TimeSlots for a specific user.
   */

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

  public List<TimeSlot> getTimeSlotsByDay(CommonTypes.Day day) {
    return timeSlotRepo.findByStartDay(day);
  }

  /**
   * Retrieves all TimeSlots based on availability.
   */

  public List<TimeSlot> getTimeSlotsByAvailability(CommonTypes.Availability availability) {
    return timeSlotRepo.findByAvailability(availability);
  }

  /**
   * Updates an existing TimeSlot.
   */

  public TimeSlot updateTimeSlot(int tid, TimeSlot updatedTimeSlot) {
    TimeSlot existingTimeSlot = getTimeSlotById(tid);
    existingTimeSlot.setUser(updatedTimeSlot.getUser());
//    existingTimeSlot.setDay(updatedTimeSlot.getDay());
    existingTimeSlot.setStartDay(updatedTimeSlot.getStartDay());
    existingTimeSlot.setEndDay(updatedTimeSlot.getEndDay());
    existingTimeSlot.setStartTime(updatedTimeSlot.getStartTime());
    existingTimeSlot.setEndTime(updatedTimeSlot.getEndTime());
    existingTimeSlot.setAvailability(updatedTimeSlot.getAvailability());
    return timeSlotRepo.save(existingTimeSlot);
  }

  /**
   * deletes an existing TimeSlot from id.
   */

  public void deleteTimeSlot(int tid) {
    if (!timeSlotRepo.existsById(tid)) {
      throw new RuntimeException("TimeSlot not found with id: " + tid);
    }
    timeSlotRepo.deleteById(tid);
  }
}

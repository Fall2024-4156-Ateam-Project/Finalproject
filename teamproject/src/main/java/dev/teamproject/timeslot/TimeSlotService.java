package dev.teamproject.timeslot;

import dev.teamproject.common.CommonTypes;
import dev.teamproject.common.Pair;
import dev.teamproject.user.User;
import dev.teamproject.user.UserService;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service class for managing TimeSlot entities. This class provides functionality for creating,
 * retrieving, updating, and deleting time slots.
 */

@Service
public class TimeSlotService {

  private final TimeSlotRepo timeSlotRepo;
  private final UserService userService;
  private final Object lock = new Object();


  private final TimeSlotHelper timeSlotHelper;

  @Autowired
  public TimeSlotService(TimeSlotRepo timeSlotRepo, UserService userService,
      TimeSlotHelper timeSlotHelper) {
    this.timeSlotRepo = timeSlotRepo;
    this.userService = userService;
    this.timeSlotHelper = timeSlotHelper;
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
   * Given a new timeslot, this method update or create new timeslots **Assume non overlapping
   * timeslots** 5 outcomes: 1. expand existing single timeslot: expand left or right 2. divided
   * existing single timeslot: into 2 or 3 3. cover multiple timeslots: do override and create new 2
   * or 3 timeslots 4. trivial: create new single timeslots Assumptions : 1. The timeslot cannot
   * wrap 2. The existing timeslots are not overlapping.
   * <p>
   * Assumption: exclusive time interval, this method should be used under transaction lock
   *
   * @param proposedTimeslot
   * @return
   */


  public void mergeOrUpdateTimeSlot(TimeSlot proposedTimeslot) {
    // since assumption no previous overlap timeslots
    // we only care the first and last timeslot(if existed) in the affected list
    // the middle part timeslots will be override
    // |-?-|----new----|-?-|
    // |-?-|----new--------|
    // |--------new----|-?-|
    // |--------new--------|
    System.out.println("mergeOrUpdateTimeSlot called proposedTimeslot" + proposedTimeslot);
    TimeSlot timeSlot;
    User user = userService.findById(proposedTimeslot.getUser().getUid());
    Optional<TimeSlot> maybeTimeSlot = timeSlotRepo.findById(proposedTimeslot.getTid());
    if (maybeTimeSlot.isPresent()) {
      timeSlot = maybeTimeSlot.get();
      timeSlot.setStartDay(proposedTimeslot.getStartDay());
      timeSlot.setStartTime(proposedTimeslot.getStartTime());
      timeSlot.setEndDay(proposedTimeslot.getEndDay());
      timeSlot.setEndTime(proposedTimeslot.getEndTime());
      timeSlot.setAvailability(proposedTimeslot.getAvailability());
    } else {
      timeSlot = proposedTimeslot;
    }

    List<TimeSlot> timeSlotList = this.getAllTimeSlots(); //assume non overlapping
    // time slot sort: start(weekday + time) - end(weekday + time)
    Collections.sort(timeSlotList, new TimeSlotComparator());
    List<TimeSlot> affectedSlots = new ArrayList<>();
    for (TimeSlot ts : timeSlotList) {
      System.out.println(ts);
      // if the timeslot are overlapped
      if (timeSlotHelper.isOverlapped(ts, timeSlot)) {
        //  System.out.println(
        //  "overlap found" + ts.getStartDay().toString() + timeSlot.getStartDay().toString());
        if (ts.getTid() != timeSlot.getTid()) {
          affectedSlots.add(ts);
          System.out.println("Affeceted" + ts + "////" + timeSlot);
        }
      }
    }

    if (affectedSlots.isEmpty()) {
      // Case 4: Trivial - No overlap, create a new single timeslot
      timeSlotRepo.save(timeSlot);
      System.out.println("Added new:::::::" + timeSlot);
      return;
    }
    List<TimeSlot> resultSlots = new ArrayList<>();

    TimeSlot firstSlot = affectedSlots.get(0);
    TimeSlot lastSlot = affectedSlots.get(affectedSlots.size() - 1);

    int absStartTimeNew = timeSlotHelper.absTime(timeSlot.getStartDay(), timeSlot.getStartTime());
    int absEndTimeNew = timeSlotHelper.absTime(timeSlot.getEndDay(), timeSlot.getEndTime());

    int absStartTimeFirst = timeSlotHelper.absTime(firstSlot.getStartDay(),
        firstSlot.getStartTime());
    int absEndTimeFirst = timeSlotHelper.absTime(firstSlot.getEndDay(), firstSlot.getEndTime());

    int absStartTimeLast = timeSlotHelper.absTime(lastSlot.getStartDay(), lastSlot.getStartTime());
    int absEndTimeLast = timeSlotHelper.absTime(lastSlot.getEndDay(), lastSlot.getEndTime());

    if (absStartTimeFirst < absStartTimeNew && absEndTimeLast > absEndTimeNew) {
      // if left middle right segmentation
      TimeSlot left = new TimeSlot(
          user,
          firstSlot.getStartDay(),
          timeSlotHelper.getDayAndTimeFromAbs(absStartTimeNew - 1).getKey(),
          firstSlot.getStartTime(),
          timeSlotHelper.getDayAndTimeFromAbs(absStartTimeNew - 1).getValue(),
          firstSlot.getAvailability());
      left.setTid(firstSlot.getTid()); // keep the same tid for update

      TimeSlot right = new TimeSlot(
          user,
          timeSlotHelper.getDayAndTimeFromAbs(absEndTimeNew + 1).getKey(),
          lastSlot.getEndDay(),
          timeSlotHelper.getDayAndTimeFromAbs(absEndTimeNew + 1).getValue(),
          lastSlot.getEndTime(), lastSlot.getAvailability());
      right.setTid(lastSlot.getTid());

      resultSlots.add(left);
      resultSlots.add(timeSlot);
      resultSlots.add(right);
    } else if (absStartTimeFirst < absStartTimeNew) {
      TimeSlot left = new TimeSlot(
          user,
          firstSlot.getStartDay(),
          timeSlotHelper.getDayAndTimeFromAbs(absStartTimeNew - 1).getKey(),
          firstSlot.getStartTime(),
          timeSlotHelper.getDayAndTimeFromAbs(absStartTimeNew - 1).getValue(),
          firstSlot.getAvailability()
      );
      left.setTid(firstSlot.getTid());

      resultSlots.add(left);
      resultSlots.add(timeSlot);
    } else if (absEndTimeLast > absEndTimeNew) {
      TimeSlot right = new TimeSlot(
          user,
          timeSlotHelper.getDayAndTimeFromAbs(absEndTimeNew + 1).getKey(),
          lastSlot.getEndDay(),
          timeSlotHelper.getDayAndTimeFromAbs(absEndTimeNew + 1).getValue(),
          lastSlot.getEndTime(),
          lastSlot.getAvailability()
      );
      right.setTid(lastSlot.getTid());

      resultSlots.add(timeSlot);
      resultSlots.add(right);

      // Case 4: Fully Covered
    } else {
      resultSlots.add(timeSlot);
    }
    System.out.println("affectedSlots" + affectedSlots);
    timeSlotRepo.deleteAll(affectedSlots);
    timeSlotRepo.saveAll(resultSlots);
    for (TimeSlot ts : resultSlots) {
      System.out.println("Added new:::::::" + ts);
    }

    for (TimeSlot ts : affectedSlots) {
      System.out.println("Removed:::::::" + ts);
    }
  }

  /**
   * Handle create time slot
   *
   * @param newTimeSlot
   * @return
   */
  @Transactional
  public TimeSlot handleTimeSlotCreation(TimeSlot newTimeSlot) {
    // need to start the lock before timeslot manipulation
    synchronized (lock) {
      if (timeSlotHelper.isWrapped(newTimeSlot)) {
        Pair<CommonTypes.Day, LocalTime> weekEnd = timeSlotHelper.getWeekEnd();
        Pair<CommonTypes.Day, LocalTime> weekStart = timeSlotHelper.getWeekStart();
        // First part: From start to the end of the week
        System.out.println("Wrap around");
        TimeSlot part1 = new TimeSlot(
            newTimeSlot.getUser(),
            newTimeSlot.getStartDay(),
            weekEnd.getKey(),
            newTimeSlot.getStartTime(),
            weekEnd.getValue(),
            newTimeSlot.getAvailability()
        );

        TimeSlot part2 = new TimeSlot(
            newTimeSlot.getUser(),
            weekStart.getKey(),
            newTimeSlot.getEndDay(),
            weekStart.getValue(),
            newTimeSlot.getEndTime(),
            newTimeSlot.getAvailability()
        );
        if (!part2.getStartTime().equals(part2.getEndTime()) || !part2.getStartDay()
            .equals(part2.getEndDay())) {
          mergeOrUpdateTimeSlot(part1);
          mergeOrUpdateTimeSlot(part2);
          System.out.println("Processing first part: " + part1);
          System.out.println("Processing second part: " + part2);
        } else {
          System.out.println("Skipping invalid second part: " + part2);
          mergeOrUpdateTimeSlot(part1);
        }
      } else {
        System.out.println("Handling non-wrap-around timeslot: " + newTimeSlot);
        mergeOrUpdateTimeSlot(newTimeSlot);
      }
    }
    return newTimeSlot;
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

  public List<TimeSlot> getTimeSlotsByUserEmailSortedByDate(String email) {
    List<User> users = userService.findByEmail(email);
    List<TimeSlot> timeslots = new ArrayList<>();
    for (User user : users) {
      timeslots.addAll(timeSlotRepo.findByUser(user));
    }
    Collections.sort(timeslots, new TimeSlotComparator());
    return timeslots;
  }

  /**
   * Retrieves all TimeSlots for a specific day.
   */
  @Transactional(readOnly = true)
  public List<TimeSlot> getTimeSlotsByDay(CommonTypes.Day day) {
    return timeSlotRepo.findByStartDay(day);
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
      existingTimeSlot.setStartDay(updatedTimeSlot.getStartDay());
      existingTimeSlot.setEndDay(updatedTimeSlot.getEndDay());
      existingTimeSlot.setStartTime(updatedTimeSlot.getStartTime());
      existingTimeSlot.setEndTime(updatedTimeSlot.getEndTime());
      existingTimeSlot.setAvailability(updatedTimeSlot.getAvailability());
      return timeSlotRepo.save(existingTimeSlot);
    }

  }

  /**
   * Updates an existing TimeSlot. Ensures thread safety with synchronized block.
   */
  @Transactional
  public TimeSlot updateTimeSlotNoOverlap(int tid, TimeSlot updatedTimeSlot) {
    synchronized (lock) {
      updatedTimeSlot.setTid(tid);
      mergeOrUpdateTimeSlot(updatedTimeSlot);
    }
    return updatedTimeSlot;
  }


  /**
   * Check if the time slot Update request valid
   *
   * @param tid      the updated target
   * @param timeSlot the proposed slot
   * @return
   */

  @Transactional(readOnly = true)
  public Boolean isTimeSlotUpdateRequestValid(int tid, TimeSlot timeSlot) {
    Optional<TimeSlot> ts = this.timeSlotRepo.findById(tid);
    if (ts.isEmpty()) {
      return false;
    }
    if (timeSlot.getUser().equals(ts.get().getUser())) {
      return false;
    }
    return true;
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

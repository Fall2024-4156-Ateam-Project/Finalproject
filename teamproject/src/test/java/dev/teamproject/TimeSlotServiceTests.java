package dev.teamproject;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import dev.teamproject.common.CommonTypes;
import dev.teamproject.common.Pair;
// import dev.teamproject.common.CommonTypes.Availability;
// import dev.teamproject.common.CommonTypes.Day;
// import dev.teamproject.common.Pair;
import dev.teamproject.timeslot.TimeSlot;
import dev.teamproject.timeslot.TimeSlotComparator;
import dev.teamproject.timeslot.TimeSlotHelper;
import dev.teamproject.timeslot.TimeSlotRepo;
import dev.teamproject.timeslot.TimeSlotService;
// import dev.teamproject.timeslot.;
import dev.teamproject.user.User;
import dev.teamproject.user.UserService;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.hibernate.mapping.Component;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.transaction.annotation.Transactional;
import org.yaml.snakeyaml.comments.CommentType;

class TimeSlotServiceTests {

  @InjectMocks
  private TimeSlotService timeSlotService;

  @Mock
  private TimeSlotRepo timeSlotRepo;

  @Mock
  private UserService userService;

  private TimeSlot timeSlot;
  private TimeSlot wrappedTimeSlot;
  private TimeSlot wrappedTimeSlot2;
  private TimeSlotHelper timeSlotHelper;
  private User user;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
    user = new User();
    user.setEmail("johndoe123@gmail.com");
    timeSlot = new TimeSlot();
    timeSlot.setTid(1);
    timeSlot.setUser(user);
    timeSlot.setStartDay(CommonTypes.Day.Thursday);
    timeSlot.setStartTime(LocalTime.NOON);
    timeSlot.setEndDay(CommonTypes.Day.Friday);
    timeSlot.setEndTime(LocalTime.of(20, 0));

    wrappedTimeSlot = new TimeSlot();
    wrappedTimeSlot.setTid(2);
    wrappedTimeSlot.setUser(user);
    wrappedTimeSlot.setStartDay(CommonTypes.Day.Thursday);
    wrappedTimeSlot.setStartTime(LocalTime.NOON);
    wrappedTimeSlot.setEndDay(CommonTypes.Day.Monday);
    wrappedTimeSlot.setEndTime(LocalTime.of(20, 0));

    wrappedTimeSlot2 = new TimeSlot();
    wrappedTimeSlot2.setTid(3);
    wrappedTimeSlot2.setUser(user);
    wrappedTimeSlot2.setStartDay(CommonTypes.Day.Thursday);
    wrappedTimeSlot2.setStartTime(LocalTime.NOON);
    wrappedTimeSlot2.setEndDay(CommonTypes.Day.Monday);
    wrappedTimeSlot2.setEndTime(LocalTime.of(0, 0));
  }

  @Test
  void testCreateTimeSlot() {
    when(userService.findById(user.getUid())).thenReturn(user);
    when(timeSlotRepo.save(timeSlot)).thenReturn(timeSlot);
    TimeSlot createdTimeSlot = timeSlotService.createTimeSlot(timeSlot);
    assertEquals(timeSlot, createdTimeSlot);
  }

  @Test
  void testGetTimeSlotById() {
    int tid = 1;
    when(timeSlotRepo.findById(tid)).thenReturn(Optional.of(timeSlot));
    TimeSlot retrievedTimeSlot = timeSlotService.getTimeSlotById(tid);
    assertEquals(timeSlot, retrievedTimeSlot);
  }

  @Test
  void testGetAllTimeSlots() {
    List<TimeSlot> timeSlots = Collections.singletonList(timeSlot);
    when(timeSlotRepo.findAll()).thenReturn(timeSlots);
    List<TimeSlot> retrievedTimeSlots = timeSlotService.getAllTimeSlots();
    assertEquals(timeSlots, retrievedTimeSlots);
  }

  @Test
  void testGetTimeSlotsByUser() {
    when(userService.findById(user.getUid())).thenReturn(user);
    List<TimeSlot> timeSlots = Collections.singletonList(timeSlot);
    when(timeSlotRepo.findByUser(user)).thenReturn(timeSlots);
    List<TimeSlot> retrievedTimeSlots = timeSlotService.getTimeSlotsByUser(user.getUid());
    assertEquals(timeSlots, retrievedTimeSlots);
  }

  @Test
  void testGetTimeSlotsByUserEmail() {

    List<User> users = Collections.singletonList(user);
    when(userService.findByEmail(user.getEmail())).thenReturn(users);
    List<TimeSlot> timeSlots = new ArrayList<>();
    List<TimeSlot> userTimeSlots = Collections.singletonList(timeSlot);
    for (User user : users) {
      when(timeSlotRepo.findByUser(user)).thenReturn(userTimeSlots);
      timeSlots.addAll(userTimeSlots);
    }
    List<TimeSlot> retrievedTimeSlots = timeSlotService.getTimeSlotsByUserEmail(user.getEmail());
    assertEquals(timeSlots, retrievedTimeSlots);
  }

  @Test
  void testGetTimeSlotsByUserEmailSortedByDate() {

    List<User> users = Collections.singletonList(user);
    when(userService.findByEmail(user.getEmail())).thenReturn(users);
    List<TimeSlot> timeSlots = new ArrayList<>();
    List<TimeSlot> userTimeSlots = Collections.singletonList(timeSlot);
    for (User user : users) {
      when(timeSlotRepo.findByUser(user)).thenReturn(userTimeSlots);
      timeSlots.addAll(userTimeSlots);
    }
    Collections.sort(timeSlots, new TimeSlotComparator());
    List<TimeSlot> retrievedTimeSlots = timeSlotService.getTimeSlotsByUserEmail(user.getEmail());
    assertEquals(timeSlots, retrievedTimeSlots);
  }

  // @Test
  // void testHandleTimeSlotCreation(){
  //   // when(timeSlotHelper.isWrapped(timeSlot)).thenReturn(false);
  //   // when(timeSlotHelper.absTime(any(CommonTypes.Day.class), any(LocalTime.class))).thenReturn(0); // Mock absTime

  //   when(userService.findById(user.getUid())).thenReturn(user);
  //   when(timeSlotRepo.save(any(TimeSlot.class))).thenReturn(timeSlot);
  //   TimeSlot handledTimeSlot = timeSlotService.handleTimeSlotCreation(timeSlot);
  //   assertEquals(timeSlot, handledTimeSlot);
  // }

  // @Test
  // void testMergeOrUpdateTimeSlotCreation(){
  //   TimeSlot updatedTimeSlot = timeSlotService.mergeOrUpdateTimeSlot(timeSlot);
  //   assertEquals(timeSlot, updatedTimeSlot);
  // }

  // @Test
  // void testIntegrationHandleandUpdateCreation() {
  //   // handle all three cases - wrapped (normal, part2 start=end eg-strt=end=12am
  //   // monday), unwrapped
  //   // get the timslot from test data - create three test timeslots to test the
  //   // entire functionality
  //   // Declare variable

  //   // Case 1: Unwrapped timeSlot send to mergeOrUpdate fn and get back resulting ts
  //   TimeSlot resultUnwrappedTimeSlot = timeSlotService.handleTimeSlotCreation(timeSlot);
  //   TimeSlot expectedUnwrappedTimeSlot = new TimeSlot();
  //   when(timeSlotService.mergeOrUpdateTimeSlot(timeSlot)).thenReturn(expectedUnwrappedTimeSlot);
  //   //add check for correct integration
  //   // assertEquals(expectedUnwrappedTimeSlot, resultUnwrappedTimeSlot);

  //   // Case 2 wrapped normal (first if branch), check if normal(second if), send to
  //   // mergeOrUpdate fn and get back resulting ts
  //   Pair<CommonTypes.Day, LocalTime> weekEnd = timeSlotHelper.getDayAndTimeFromAbs(7 * 24 * 60 - 1); // End of the week
  //   Pair<CommonTypes.Day, LocalTime> weekStart = timeSlotHelper.getDayAndTimeFromAbs(0); // Start of the week

  //   TimeSlot resultWrappedTimeSlot = timeSlotService.handleTimeSlotCreation(wrappedTimeSlot);
  //   TimeSlot expectedWrappedTimeSlot = new TimeSlot();

  //   System.out.println("Wrap around");
  //   TimeSlot part1 = new TimeSlot(
  //       wrappedTimeSlot.getUser(),
  //       wrappedTimeSlot.getStartDay(),
  //       weekEnd.getKey(),
  //       wrappedTimeSlot.getStartTime(),
  //       weekEnd.getValue(),
  //       wrappedTimeSlot.getAvailability());

  //   TimeSlot part2 = new TimeSlot(
  //       wrappedTimeSlot.getUser(),
  //       weekStart.getKey(),
  //       wrappedTimeSlot.getEndDay(),
  //       weekStart.getValue(),
  //       wrappedTimeSlot.getEndTime(),
  //       wrappedTimeSlot.getAvailability());

  //   when(timeSlotService.mergeOrUpdateTimeSlot(part1)).thenReturn(part1);
  //   when(timeSlotService.mergeOrUpdateTimeSlot(part2)).thenReturn(part2);
  //   //add check for correct integration testing
  //   // assertEquals(expectedWrappedTimeSlot, resultWrappedTimeSlot);

  //   // case 3 wrapped abnormal (if, then else branch), send only part1
  //   TimeSlot resultWrappedTimeSlot2 = timeSlotService.handleTimeSlotCreation(wrappedTimeSlot2);
  //   TimeSlot expectedWrappedTimeSlot2 = new TimeSlot();

  //   System.out.println("Wrap around");
  //   TimeSlot wrappedPart1 = new TimeSlot(
  //       wrappedTimeSlot2.getUser(),
  //       wrappedTimeSlot2.getStartDay(),
  //       weekEnd.getKey(),
  //       wrappedTimeSlot2.getStartTime(),
  //       weekEnd.getValue(),
  //       wrappedTimeSlot2.getAvailability());

  //   when(timeSlotService.mergeOrUpdateTimeSlot(part1)).thenReturn(part1);
    
  // }
  // Src code to be tested

  // Also add test for mergeOrUpdate

  // end

  @Test
  void testGetTimeSlotsByStartDay() {
    CommonTypes.Day day = CommonTypes.Day.Monday;
    List<TimeSlot> timeSlots = Collections.singletonList(timeSlot);
    when(timeSlotRepo.findByStartDay(day)).thenReturn(timeSlots);
    List<TimeSlot> retrievedTimeSlots = timeSlotService.getTimeSlotsByDay(day);
    assertEquals(timeSlots, retrievedTimeSlots);
  }

  @Test
  void testGetTimeSlotsByAvailability() {
    CommonTypes.Availability availability = CommonTypes.Availability.available;
    List<TimeSlot> timeSlots = Collections.singletonList(timeSlot);
    when(timeSlotRepo.findByAvailability(availability)).thenReturn(timeSlots);
    List<TimeSlot> retrievedTimeSlots = timeSlotService.getTimeSlotsByAvailability(availability);
    assertEquals(timeSlots, retrievedTimeSlots);
  }

  @Test
  void testUpdateTimeSlot() {
    int tid = 1;
    when(timeSlotRepo.findById(tid)).thenReturn(Optional.of(timeSlot));
    when(timeSlotRepo.save(timeSlot)).thenReturn(timeSlot);

    TimeSlot updatedTimeSlot = timeSlotService.updateTimeSlot(tid, timeSlot);
    assertEquals(timeSlot, updatedTimeSlot);
  }

  @Test
  void testDeleteTimeSlot() {
    int tid = 1;
    when(timeSlotRepo.existsById(tid)).thenReturn(true);
    timeSlotService.deleteTimeSlot(tid);
  }

  @Test
  void testDeleteTimeSlotNotFound() {
    int tid = 1;
    when(timeSlotRepo.existsById(tid)).thenReturn(false);
    assertThrows(RuntimeException.class, () -> timeSlotService.deleteTimeSlot(tid));
  }
}

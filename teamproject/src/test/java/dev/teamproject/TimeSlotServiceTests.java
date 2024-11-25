package dev.teamproject;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import dev.teamproject.common.CommonTypes;
import dev.teamproject.common.CommonTypes.Availability;
import dev.teamproject.common.CommonTypes.Day;
import dev.teamproject.common.Pair;
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
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.hibernate.mapping.Component;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.mock.mockito.MockBean;

class TimeSlotServiceTests {

  @Mock
  private TimeSlotHelper timeSlotHelper;
  @InjectMocks
  private TimeSlotService timeSlotService;

  @Mock
  private TimeSlotRepo timeSlotRepo;

  @Mock
  private UserService userService;

  private TimeSlot timeSlot;
  private TimeSlot wrappedTimeSlot;
  private TimeSlot wrappedTimeSlot2;
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
    when(userService.findById(anyInt())).thenReturn(user);
    when(userService.findByEmail(anyString())).thenReturn(Arrays.asList(user));
  }

  @Test
  void testCreateTimeSlot() {
    when(userService.findById(user.getUid())).thenReturn(user);
    when(timeSlotRepo.save(timeSlot)).thenReturn(timeSlot);
    TimeSlot createdTimeSlot = timeSlotService.createTimeSlot(timeSlot);
    assertEquals(timeSlot, createdTimeSlot);
  }

  @Test
  public void testMergeOrUpdateTimeSlot_nonOverlapping() {
    // Arrange
    TimeSlot newTimeSlot = new TimeSlot(user, Day.Monday, Day.Monday, LocalTime.of(9, 0), LocalTime.of(10, 0), Availability.busy);
    when(timeSlotRepo.findAll()).thenReturn(Collections.emptyList());

    // Act
    timeSlotService.mergeOrUpdateTimeSlot(newTimeSlot);

    // Assert
    verify(timeSlotRepo, times(1)).save(newTimeSlot);
    verify(timeSlotRepo, never()).deleteAll(any());
  }


  @Test
  public void testMergeOrUpdateTimeSlot_withOverlap() {
    // Arrange: Existing timeslot
    TimeSlot existing = new TimeSlot(
        user,
        Day.Monday,
        Day.Monday,
        LocalTime.of(8, 0),
        LocalTime.of(11, 0),
        Availability.busy
    );

    // New timeslot to be merged, has the highest priority
    TimeSlot newTimeSlot = new TimeSlot(
        user,
        Day.Monday,
        Day.Monday,
        LocalTime.of(9, 0),
        LocalTime.of(10, 0),
        Availability.available
    );

    when(timeSlotRepo.findAll()).thenReturn(Collections.singletonList(existing));
    when(timeSlotHelper.isOverlapped(any(TimeSlot.class), any(TimeSlot.class))).thenReturn(true);
    when(timeSlotHelper.absTime(any(Day.class), any(LocalTime.class)))
        .thenAnswer(invocation -> {
          Day day = invocation.getArgument(0);
          LocalTime time = invocation.getArgument(1);
          return day.ordinal() * 1440 + time.getHour() * 60 + time.getMinute();
        });
    when(timeSlotHelper.getDayAndTimeFromAbs(anyInt()))
        .thenAnswer(invocation -> {
          int absTime = invocation.getArgument(0);
          Day day = Day.values()[absTime / 1440]; // Assuming 1440 minutes per day
          LocalTime time = LocalTime.of((absTime % 1440) / 60, absTime % 60);
          return new Pair<>(day, time);
        });

    // Act: Call the method under test
    timeSlotService.mergeOrUpdateTimeSlot(newTimeSlot);

    // Assert: Capture and verify the saved timeslots
    ArgumentCaptor<List<TimeSlot>> captor = ArgumentCaptor.forClass(List.class);
    verify(timeSlotRepo).deleteAll(anyList());
    verify(timeSlotRepo).saveAll(captor.capture());

    List<TimeSlot> savedSlots = captor.getValue();
    assertEquals(3, savedSlots.size());

    // Validate the left, middle, and right segments
    TimeSlot left = savedSlots.get(0);
    TimeSlot middle = savedSlots.get(1);
    TimeSlot right = savedSlots.get(2);

    assertEquals(Day.Monday, left.getStartDay());
    assertEquals(LocalTime.of(8, 0), left.getStartTime());
    assertEquals(LocalTime.of(8, 59), left.getEndTime());

    assertEquals(Day.Monday, middle.getStartDay());
    assertEquals(LocalTime.of(9, 0), middle.getStartTime());
    assertEquals(LocalTime.of(10, 0), middle.getEndTime());

    assertEquals(Day.Monday, right.getStartDay());
    assertEquals(LocalTime.of(10, 1), right.getStartTime());
    assertEquals(LocalTime.of(11, 0), right.getEndTime());
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

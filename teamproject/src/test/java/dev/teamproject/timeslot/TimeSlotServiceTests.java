package dev.teamproject.timeslot;

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
import dev.teamproject.user.User;
import dev.teamproject.user.UserService;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
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
    TimeSlot newTimeSlot = new TimeSlot(user, Day.Monday, Day.Monday,
            LocalTime.of(9, 0), LocalTime.of(10, 0),
        Availability.busy);
    when(timeSlotRepo.findAll()).thenReturn(Collections.emptyList());

    // Act
    timeSlotService.mergeOrUpdateTimeSlot(newTimeSlot);

    // Assert
    verify(timeSlotRepo, times(1)).save(newTimeSlot);
    verify(timeSlotRepo, never()).deleteAll(any());
  }

  @Test
  public void testMergeOrUpdateTimeSlot_withOverlap() {
    // existing timeslot
    TimeSlot existing = new TimeSlot(
        user,
        Day.Monday,
        Day.Monday,
        LocalTime.of(8, 0),
        LocalTime.of(11, 0),
        Availability.busy);
    existing.setTid(1);
    existing.setUser(user);

    TimeSlot newTimeSlot = new TimeSlot(
        user,
        Day.Monday,
        Day.Monday,
        LocalTime.of(9, 0),
        LocalTime.of(10, 0),
        Availability.available);
    newTimeSlot.setTid(2);
    newTimeSlot.setUser(user);

    when(userService.findById(user.getUid())).thenReturn(user);
    when(timeSlotRepo.findById(newTimeSlot.getTid())).thenReturn(Optional.empty());
    when(timeSlotRepo.findAll()).thenReturn(Collections.singletonList(existing));
    when(timeSlotHelper.isOverlapped(existing, newTimeSlot)).thenReturn(true);
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
          LocalTime time = LocalTime.of((absTime % 1440) / 60, (absTime % 1440) % 60);
          return new Pair<>(day, time);
        });

    timeSlotService.mergeOrUpdateTimeSlot(newTimeSlot);

    ArgumentCaptor<List<TimeSlot>> deleteCaptor = ArgumentCaptor.forClass(List.class);
    verify(timeSlotRepo).deleteAll(deleteCaptor.capture());
    List<TimeSlot> deletedSlots = deleteCaptor.getValue();
    assertEquals(1, deletedSlots.size());
    assertEquals(existing, deletedSlots.get(0));

    ArgumentCaptor<List<TimeSlot>> saveCaptor = ArgumentCaptor.forClass(List.class);
    verify(timeSlotRepo).saveAll(saveCaptor.capture());
    List<TimeSlot> savedSlots = saveCaptor.getValue();
    assertEquals(3, savedSlots.size());

    savedSlots.sort(Comparator.comparing(ts -> ts.getStartTime()));

    // Validate the left, middle, and right segments
    TimeSlot left = savedSlots.get(0);
    final TimeSlot middle = savedSlots.get(1);
    final TimeSlot right = savedSlots.get(2);

    // Left segment (from existing timeslot)
    assertEquals(Day.Monday, left.getStartDay());
    assertEquals(LocalTime.of(8, 0), left.getStartTime());
    assertEquals(LocalTime.of(8, 59), left.getEndTime());
    assertEquals(Availability.busy, left.getAvailability());
    assertEquals(existing.getTid(), left.getTid()); // Should retain the same ID

    // Middle segment
    assertEquals(Day.Monday, middle.getStartDay());
    assertEquals(LocalTime.of(9, 0), middle.getStartTime());
    assertEquals(LocalTime.of(10, 0), middle.getEndTime());
    assertEquals(Availability.available, middle.getAvailability());
    assertEquals(newTimeSlot.getTid(), middle.getTid());

    // Right segment (from existing timeslot)
    assertEquals(Day.Monday, right.getStartDay());
    assertEquals(LocalTime.of(10, 1), right.getStartTime());
    assertEquals(LocalTime.of(11, 0), right.getEndTime());
    assertEquals(Availability.busy, right.getAvailability());
    assertEquals(existing.getTid(), right.getTid()); // Should retain the same ID
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
  void testHandleTimeSlotCreation_nonWrapped() {
    when(timeSlotHelper.isWrapped(timeSlot)).thenReturn(false);
    when(userService.findById(user.getUid())).thenReturn(user);
    timeSlotService.handleTimeSlotCreation(timeSlot);
    verify(timeSlotRepo, times(1)).save(timeSlot);
  }

  @Test
  void testHandleTimeSlotCreation_wrapped() {
    when(timeSlotHelper.isWrapped(timeSlot)).thenReturn(true);
    Pair<CommonTypes.Day, LocalTime> weekEnd = new Pair<>(Day.Saturday, LocalTime.MAX);
    Pair<CommonTypes.Day, LocalTime> weekStart = new Pair<>(Day.Sunday, LocalTime.MIN);
    when(timeSlotHelper.getWeekEnd()).thenReturn(weekEnd);
    when(timeSlotHelper.getWeekStart()).thenReturn(weekStart);

    timeSlotService.handleTimeSlotCreation(timeSlot);
    verify(timeSlotRepo, times(2)).save(any(TimeSlot.class));
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
  void testUpdateTimeSlotNoOverlap() {
    int tid = 1;
    when(timeSlotRepo.findById(tid)).thenReturn(Optional.of(timeSlot));
    timeSlotService.updateTimeSlotNoOverlap(tid, timeSlot);
    verify(timeSlotRepo, times(1)).save(any(TimeSlot.class));
  }

  @Test
  void testIsTimeSlotUpdateRequestValid_valid() {
    int tid = 1;
    when(timeSlotRepo.findById(tid)).thenReturn(Optional.of(timeSlot));
    timeSlot.setUser(user);
    boolean result = timeSlotService.isTimeSlotUpdateRequestValid(tid, timeSlot);
    assertEquals(false, result);
  }

  @Test
  void testIsTimeSlotUpdateRequestValid_invalid() {
    int tid = 1;
    when(timeSlotRepo.findById(tid)).thenReturn(Optional.empty());
    boolean result = timeSlotService.isTimeSlotUpdateRequestValid(tid, timeSlot);
    assertEquals(false, result);
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

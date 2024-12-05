package dev.teamproject.timeslot;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import dev.teamproject.common.CommonTypes;
import dev.teamproject.timeslot.TimeSlot;
import dev.teamproject.timeslot.TimeSlotController;
import dev.teamproject.timeslot.TimeSlotService;
import dev.teamproject.user.User;
import java.time.LocalTime;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeException;

class TimeSlotControllerTests {

  @InjectMocks
  private TimeSlotController timeSlotController;
  private User user;

  @Mock
  private TimeSlotService timeSlotService;
  @Mock
  private TimeSlot timeSlot;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);

    timeSlotController = new TimeSlotController(timeSlotService);
    user = new User();
    user.setEmail("abc@gmail.com");

    timeSlot = new TimeSlot();
    timeSlot.setTid(1);
    timeSlot.setStartDay(CommonTypes.Day.Thursday);
    timeSlot.setStartTime(LocalTime.NOON);
    timeSlot.setEndDay(CommonTypes.Day.Friday);
    timeSlot.setEndTime(LocalTime.of(20, 0));    
    timeSlot.setUser(user);
  }

  @Test
  void testCreateTimeSlot() {
    when(timeSlotService.createTimeSlot(timeSlot)).thenReturn(timeSlot);
    ResponseEntity<TimeSlot> response = timeSlotController.createTimeSlot(timeSlot);
    assertEquals(200, response.getStatusCodeValue());
    assertEquals(timeSlot, response.getBody());
  }

  @Test
  void testCreateMergeTimeSlot() {
    when(timeSlotService.handleTimeSlotCreation(timeSlot)).thenReturn(timeSlot);
    ResponseEntity<TimeSlot> response = timeSlotController.createMergeTimeSlot(timeSlot);
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals(timeSlot, response.getBody());
  }

  @Test
  void testGetTimeSlotById() {
    int tid = 1;
    when(timeSlotService.getTimeSlotById(tid)).thenReturn(timeSlot);
    ResponseEntity<TimeSlot> response = timeSlotController.getTimeSlotById(tid);
    assertEquals(200, response.getStatusCodeValue());
    assertEquals(timeSlot, response.getBody());
  }

  @Test
  void testGetAllTimeSlots() {
    List<TimeSlot> timeSlots = Collections.singletonList(timeSlot);
    when(timeSlotService.getAllTimeSlots()).thenReturn(timeSlots);
    ResponseEntity<List<TimeSlot>> response = timeSlotController.getAllTimeSlots();
    assertEquals(200, response.getStatusCodeValue());
    assertEquals(timeSlots, response.getBody());
  }

  @Test
  void testGetTimeSlotsByUser() {
    int uid = 1;
    List<TimeSlot> timeSlots = Collections.singletonList(timeSlot);
    when(timeSlotService.getTimeSlotsByUser(uid)).thenReturn(timeSlots);
    ResponseEntity<List<TimeSlot>> response = timeSlotController.getTimeSlotsByUser(uid);
    assertEquals(200, response.getStatusCodeValue());
    assertEquals(timeSlots, response.getBody());
  }

  @Test
  void testGetTimeSlotsByUserEmail() {
    String uEmail = "abc@gmail.com";
    // int uid = 1;
    List<TimeSlot> timeSlots = Collections.singletonList(timeSlot);
    when(timeSlotService.getTimeSlotsByUserEmailSortedByDate(uEmail)).thenReturn(timeSlots);
    // when(timeSlotService.getTimeSlotsByUser(uid)).thenReturn(timeSlots);
    ResponseEntity<List<TimeSlot>> response = timeSlotController.getTimeSlotsByUserEmail(uEmail);
    // assertEquals(200, response.getStatusCodeValue());
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals(timeSlots, response.getBody());
  }

  @Test
  void testGetTimeSlotsByDay() {
    CommonTypes.Day day = CommonTypes.Day.Monday;
    List<TimeSlot> timeSlots = Collections.singletonList(timeSlot);
    when(timeSlotService.getTimeSlotsByDay(day)).thenReturn(timeSlots);
    ResponseEntity<List<TimeSlot>> response = timeSlotController.getTimeSlotsByDay(day);
    assertEquals(200, response.getStatusCodeValue());
    assertEquals(timeSlots, response.getBody());
  }

  @Test
  void testGetTimeSlotsByAvailability() {
    CommonTypes.Availability availability = CommonTypes.Availability.available;
    List<TimeSlot> timeSlots = Collections.singletonList(timeSlot);
    when(timeSlotService.getTimeSlotsByAvailability(availability)).thenReturn(timeSlots);
    ResponseEntity<List<TimeSlot>> response = timeSlotController
            .getTimeSlotsByAvailability(availability);
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals(timeSlots, response.getBody());
  }

  @Test
  void testUpdateTimeSlot() {
    int tid = 1;
    when(timeSlotService.updateTimeSlot(tid, timeSlot)).thenReturn(timeSlot);
    ResponseEntity<TimeSlot> response = timeSlotController.updateTimeSlot(tid, timeSlot);
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals(timeSlot, response.getBody());
  }

  @Test
  void testUpdateTimeSlotNoOverlap() {
    int tid = 1;
    //Valid
    when(timeSlotService.isTimeSlotUpdateRequestValid(tid, timeSlot)).thenReturn(true);
    when(timeSlotService.updateTimeSlotNoOverlap(tid, timeSlot)).thenReturn(timeSlot);
    
    ResponseEntity<TimeSlot> responseNoOverlapValid = timeSlotController.updateTimeSlotNoOverlap(tid, timeSlot);
    assertEquals(HttpStatus.OK, responseNoOverlapValid.getStatusCode());
    assertEquals(timeSlot, responseNoOverlapValid.getBody());

    //Invalid
    ResponseEntity<TimeSlot> responseNoOverlapInvalid = timeSlotController.updateTimeSlotNoOverlap(300, timeSlot);
    assertEquals(HttpStatus.BAD_REQUEST, responseNoOverlapInvalid.getStatusCode());
    assertEquals(timeSlot, responseNoOverlapInvalid.getBody());
  }
  
  @Test
  void testDeleteTimeSlot() {
    int tid = 1;
    ResponseEntity<Void> response = timeSlotController.deleteTimeSlot(tid);
    assertEquals(204, response.getStatusCodeValue());
  }
}

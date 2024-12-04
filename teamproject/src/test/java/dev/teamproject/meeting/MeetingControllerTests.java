package dev.teamproject.meeting;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import dev.teamproject.apiResponse.GenericApiResponse;
import dev.teamproject.common.CommonTypes;
import dev.teamproject.user.User;

public class MeetingControllerTests {
  @Mock
  private MeetingService meetingService;

  @InjectMocks
  private MeetingController meetingController;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void testFindByRecurrence() {
    CommonTypes.Recurrence recurrence = CommonTypes.Recurrence.daily;
    Meeting meeting1 = new Meeting();
    Meeting meeting2 = new Meeting();
    List<Meeting> meetings = Arrays.asList(meeting1, meeting2);

    when(meetingService.findByRecurrence(recurrence)).thenReturn(meetings);
    List<Meeting> result = meetingController.findByRecurrence(recurrence.name());
    assertEquals(2, result.size());
    verify(meetingService, times(1)).findByRecurrence(recurrence);
  }

  @Test
  void testFindByStatus() {
    CommonTypes.MeetingStatus status = CommonTypes.MeetingStatus.Valid;
    Meeting meeting = new Meeting();
    List<Meeting> meetings = Arrays.asList(meeting);

    when(meetingService.findByStatus(status)).thenReturn(meetings);
    List<Meeting> result = meetingController.findByStatus(status.name());
    assertEquals(1, result.size());
    verify(meetingService, times(1)).findByStatus(status);
  }

  @Test
  void testFindByType() {
    CommonTypes.MeetingType type = CommonTypes.MeetingType.group;
    Meeting meeting = new Meeting();
    List<Meeting> meetings = Arrays.asList(meeting);
    when(meetingService.findByType(type)).thenReturn(meetings);
    List<Meeting> result = meetingController.findByType(type.name());
    assertEquals(1, result.size());
    verify(meetingService, times(1)).findByType(type);
  }

  @Test
  void testFindById() {
    int meetingId = 1;
    Meeting meeting = new Meeting();
    when(meetingService.findById(meetingId)).thenReturn(meeting);
    Meeting result = meetingController.findByid(meetingId);
    assertNotNull(result);
    verify(meetingService, times(1)).findById(meetingId);
  }

  @Test
  void testFindByEmail() {
    String email = "test@example.com";
    Meeting meeting = new Meeting();
    List<Meeting> meetings = Arrays.asList(meeting);

    when(meetingService.findByEmail(email)).thenReturn(meetings);
    List<Meeting> result = meetingController.findByid(email);
    assertEquals(1, result.size());
    verify(meetingService, times(1)).findByEmail(email);
  }

  @Test
  void testFindAll() {
    Meeting meeting1 = new Meeting();
    Meeting meeting2 = new Meeting();
    List<Meeting> meetings = Arrays.asList(meeting1, meeting2);
    when(meetingService.findAll()).thenReturn(meetings);
    List<Meeting> result = meetingController.findAll();

    assertEquals(2, result.size());
    verify(meetingService, times(1)).findAll();
  }

  @Test
  void testFindByOrganizer() {
    User organizer = new User();
    Meeting meeting = new Meeting();
    List<Meeting> meetings = Arrays.asList(meeting);
    when(meetingService.findByOrganizer(organizer)).thenReturn(meetings);
    List<Meeting> result = meetingController.findByOrganizer(organizer);
    assertEquals(1, result.size());
    verify(meetingService, times(1)).findByOrganizer(organizer);
  }

  @Test
  void testDeleteMeeting() {
    int meetingId = 1;
    doNothing().when(meetingService).deleteMeeting(meetingId);
    ResponseEntity<Void> response = meetingController.deleteMeeting(meetingId);
    assertEquals(204, response.getStatusCodeValue());
    verify(meetingService, times(1)).deleteMeeting(meetingId);
  }

  @Test
  void testSaveMeeting() {
    MeetingDTO meetingDTO = new MeetingDTO();
    doNothing().when(meetingService).save(meetingDTO);

    ResponseEntity<GenericApiResponse<String>> response = meetingController.saveMeeting(meetingDTO);
    assertEquals(201, response.getStatusCodeValue());
    verify(meetingService, times(1)).save(meetingDTO);
  }
}

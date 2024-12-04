package dev.teamproject.participant;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import dev.teamproject.common.CommonTypes;
import dev.teamproject.meeting.Meeting;
import dev.teamproject.meeting.MeetingService;
import dev.teamproject.user.User;
import dev.teamproject.user.UserService;

public class ParticipantControllerTests {
  private ParticipantController participantController;

  @Mock
  private ParticipantService participantService;

  @Mock
  private MeetingService meetingService;

  @Mock
  private UserService userService;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
    participantController = new ParticipantController(participantService, meetingService, userService);
  }

  @Test
  void testSaveParticipant() {
    Participant participant = new Participant();
    participantController.saveParticipant(participant);
    verify(participantService, times(1)).save(participant);
  }

  @Test
  void testDeleteParticipant() {
    int pid = 1;
    ResponseEntity<Void> response = participantController.deleteParticipant(pid);
    verify(participantService, times(1)).deleteParticipant(pid);
    assertEquals(ResponseEntity.noContent().build(), response);
  }

  @Test
  void testFindById() {
    int pid = 1;
    Participant participant = new Participant();
    when(participantService.findById(pid)).thenReturn(participant);
    Participant result = participantController.findById(pid);
    verify(participantService, times(1)).findById(pid);
    assertEquals(participant, result);
  }

  @Test
  void testFindAll() {
    List<Participant> participants = Arrays.asList(new Participant(), new Participant());
    when(participantService.findAll()).thenReturn(participants);
    List<Participant> result = participantController.findAll();
    verify(participantService, times(1)).findAll();
    assertEquals(participants, result);
  }

  @Test
  void testFindByMeeting() {
    Meeting meeting = new Meeting();
    List<Participant> participants = Arrays.asList(new Participant(), new Participant());
    when(participantService.findByMeeting(meeting)).thenReturn(participants);
    List<Participant> result = participantController.findByMeeting(meeting.getMid());
    verify(participantService, times(1)).findByMeeting(null);
  }

  @Test
  void testFindByUser() {
    User user = new User();
    user.setUid(1);
    user.setName("testname");
    user.setEmail("test@email");
    List<Participant> participants = Arrays.asList(new Participant(), new Participant());
    when(participantService.findByUser(user)).thenReturn(participants);

    List<Participant> result = participantController.findByUser(user.getUid());
    User user1=userService.findById(1);
    verify(participantService, times(1)).findByUser(user1);
  }

  @Test
  void testFindByStatus() {
    CommonTypes.ParticipantStatus status = CommonTypes.ParticipantStatus.accept;
    List<Participant> participants = Arrays.asList(new Participant(), new Participant());
    when(participantService.findByStatus(status)).thenReturn(participants);
    List<Participant> result = participantController.findByStatus(status);
    verify(participantService, times(1)).findByStatus(status);
    assertEquals(participants, result);
  }

  @Test
  void testFindByRole() {
    CommonTypes.Role role = CommonTypes.Role.organizer;
    List<Participant> participants = Arrays.asList(new Participant(), new Participant());
    when(participantService.findByRole(role)).thenReturn(participants);
    List<Participant> result = participantController.findByRole(role);
    verify(participantService, times(1)).findByRole(role);
    assertEquals(participants, result);
  }
}

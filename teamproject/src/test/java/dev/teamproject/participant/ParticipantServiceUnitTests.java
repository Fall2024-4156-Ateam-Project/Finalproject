package dev.teamproject.participant;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import dev.teamproject.common.CommonTypes;
import dev.teamproject.meeting.Meeting;
import dev.teamproject.participant.Participant;
import dev.teamproject.participant.ParticipantRepo;
import dev.teamproject.participant.ParticipantService;
import dev.teamproject.user.User;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

/**
 * This class contains unit tests for the ParticipantService class.
 * It tests the find and save methods.
 */
public class ParticipantServiceUnitTests {

  @Mock
  private ParticipantRepo participantRepo;

  @InjectMocks
  private ParticipantService participantService;

  private Participant participant;
  private Participant participant2;
  private Meeting mockMeeting;
  private User mockUser;
  private User mockUser2;
  private User organizer;

  /**
   * This method sets up the required test data, including mock users, a mock meeting,
   * and two participants.
   */
  @BeforeEach
  public void setUp() {
    MockitoAnnotations.openMocks(this);

    mockUser = new User("test1", "test1@email.com");
    mockUser2 = new User("test2", "test2@email.com");
    organizer = new User("Test Organizer", "organizer@example.com");

    mockMeeting = new Meeting();
    mockMeeting.setOrganizer(organizer);
    mockMeeting.setType(CommonTypes.MeetingType.group);
    mockMeeting.setDescription("Test Meeting");
    mockMeeting.setStartTime(LocalTime.of(10, 0));
    mockMeeting.setEndTime(LocalTime.of(11, 0));
    mockMeeting.setRecurrence(CommonTypes.Recurrence.weekly);
    mockMeeting.setCreatedAt(LocalTime.of(9, 0));
    mockMeeting.setInviteParticipant(5);
    mockMeeting.setAcceptParticipant(3);
    mockMeeting.setStatus(CommonTypes.MeetingStatus.Valid);

    participant = new Participant(mockMeeting, mockUser, CommonTypes.Role.organizer,
            CommonTypes.ParticipantStatus.waiting);

    participant2 = new Participant(mockMeeting, mockUser2, CommonTypes.Role.participant,
            CommonTypes.ParticipantStatus.accept);
  }

  @Test
  void testFindAll() {
    List<Participant> participants = Arrays.asList(participant, participant2);
    when(participantRepo.findAllByOrderByPidDesc()).thenReturn(participants);

    List<Participant> result = participantService.findAll();

    assertEquals(2, result.size());
    assertEquals("test1", result.get(0).getUser().getName());
    assertEquals("test2", result.get(1).getUser().getName());
    verify(participantRepo, times(1)).findAllByOrderByPidDesc();
  }

  @Test
  void testFindById() {
    when(participantRepo.findById(1)).thenReturn(participant);

    Participant result = participantService.findById(1);

    assertEquals("test1", result.getUser().getName());
    verify(participantRepo, times(1)).findById(1);
  }

  @Test
  void testFindByMeeting() {
    List<Participant> participants = Arrays.asList(participant, participant2);
    when(participantRepo.findByMeeting(mockMeeting)).thenReturn(participants);

    List<Participant> result = participantService.findByMeeting(mockMeeting);

    assertEquals("test1", result.get(0).getUser().getName());
    assertEquals("test2", result.get(1).getUser().getName());
    verify(participantRepo, times(1)).findByMeeting(mockMeeting);
  }

  @Test
  void testFindByUser() {
    List<Participant> participants = Arrays.asList(participant, participant2);
    when(participantRepo.findByUser(mockUser)).thenReturn(participants);

    List<Participant> result = participantService.findByUser(mockUser);

    assertEquals("test1", result.get(0).getUser().getName());
    verify(participantRepo, times(1)).findByUser(mockUser);
  }

  @Test
  void testFindByStatus() {
    List<Participant> participants = Arrays.asList(participant);
    when(participantRepo
            .findByStatus(CommonTypes.ParticipantStatus.waiting)).thenReturn(participants);

    List<Participant> result =
            participantService.findByStatus(CommonTypes.ParticipantStatus.waiting);

    assertEquals(1, result.size());
    assertEquals("test1", result.get(0).getUser().getName());
    verify(participantRepo, times(1)).findByStatus(CommonTypes.ParticipantStatus.waiting);
  }

  @Test
  void testFindByRole() {
    List<Participant> participants = Arrays.asList(participant);
    when(participantRepo.findByRole(CommonTypes.Role.organizer)).thenReturn(participants);

    List<Participant> result = participantService.findByRole(CommonTypes.Role.organizer);

    assertEquals(1, result.size());
    assertEquals(CommonTypes.Role.organizer, result.get(0).getRole());
    verify(participantRepo, times(1)).findByRole(CommonTypes.Role.organizer);
  }

  @Test
  void testSaveParticipant() {
    participantService.save(participant);
    verify(participantRepo, times(1)).save(participant);
  }

  @Test
  void testDeleteParticipantSuccess() {
    when(participantRepo.existsById(1)).thenReturn(true);

    participantService.deleteParticipant(1);

    verify(participantRepo, times(1)).deleteById(1);
  }

  @Test
  void testDeleteParticipantNotFound() {
    when(participantRepo.existsById(1)).thenReturn(false);

    RuntimeException exception = assertThrows(RuntimeException.class, () -> {
      participantService.deleteParticipant(1);
    });

    assertEquals("Participant not found with id: 1", exception.getMessage());
    verify(participantRepo, times(0)).deleteById(1);
  }  
}

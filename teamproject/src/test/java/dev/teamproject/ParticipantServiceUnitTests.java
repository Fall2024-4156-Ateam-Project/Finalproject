package dev.teamproject;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import dev.teamproject.common.CommonTypes;
import dev.teamproject.meeting.Meeting;
import dev.teamproject.participant.Participant;
import dev.teamproject.participant.ParticipantRepo;
import dev.teamproject.participant.ParticipantService;
import dev.teamproject.user.User;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

/**
 * This class contains unit tests for the participant service class.
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
   * This method creates two new participants used in test methods.
   * It creates two new users, an organizer and a meeting,
   * for the newly created participants.
   */
  
  @BeforeEach
  public void setUp() {
    MockitoAnnotations.openMocks(this);
    mockUser = new dev.teamproject.user.User("test1", "test1@email.com");
    mockUser2 = new dev.teamproject.user.User("test2", "test1@email.com");
    organizer = new dev.teamproject.user.User("Test Organizer", "organizer@example.com");
    mockMeeting = new dev.teamproject.meeting.Meeting();
    mockMeeting.setOrganizer(organizer);
    mockMeeting.setType(dev.teamproject.common.CommonTypes.MeetingType.group);
    mockMeeting.setDescription("Test Meeting");
    mockMeeting.setStartTime(Timestamp.valueOf("2024-01-01 10:00:00"));
    mockMeeting.setEndTime(Timestamp.valueOf("2024-01-01 11:00:00"));
    mockMeeting.setRecurrence(dev.teamproject.common.CommonTypes.Recurrence.weekly);
    mockMeeting.setCreatedAt(Timestamp.valueOf("2024-01-01 09:00:00"));
    mockMeeting.setInviteParticipant(5);
    mockMeeting.setAcceptParticipant(3);
    mockMeeting.setStatus(dev.teamproject.common.CommonTypes.MeetingStatus.Valid);
    
    participant = new Participant(mockMeeting, mockUser, CommonTypes.Role.organizer,
      CommonTypes.ParticipantStatus.waiting);
    participant2 = new Participant(mockMeeting, mockUser2,
      CommonTypes.Role.participant,
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
    //    verify(participantRepo, times(1)).findAllByOrderByUidDesc();
  }
  
  @Test
  void testFindById() {
    when(participantRepo.findById(1)).thenReturn(participant);
    
    Participant result = participantService.findById(1);
    
    assertEquals("test1", result.getUser().getName());
    verify(participantRepo, times(1)).findById(1);
  }
  
  //Email
  @Test
  void testFindByMeeting() {
    List<Participant> participants = Arrays.asList(participant, participant2);
    when(participantRepo.findByMeeting(mockMeeting)).thenReturn(participants);
    
    List<Participant> result = participantService.findByMeeting(mockMeeting);
    //
    ////    assertEquals(1, result.size());
    assertEquals("test1", result.get(0).getUser().getName());
    verify(participantRepo, times(1)).findByMeeting(mockMeeting);
  }
  
  //Name
  @Test
  void testFindByUser() {
    List<Participant> participants = Arrays.asList(participant, participant2);
    when(participantRepo.findByUser(mockUser)).thenReturn(participants);
    
    List<Participant> result = participantService.findByUser(mockUser);
    
    //    assertEquals(1, result.size());
    assertEquals("test1", result.get(0).getUser().getName());
    verify(participantRepo, times(1)).findByUser(mockUser);
  }
  
  @Test
  void testFindByStatus() {
    List<Participant> participants = Arrays.asList(participant, participant2);
    when(participantRepo.findByStatus(
      CommonTypes.ParticipantStatus.waiting)).thenReturn(participants);
    
    List<Participant> result = participantService.findByStatus(
        CommonTypes.ParticipantStatus.waiting);
    
    //    assertEquals(1, result.size());
    assertEquals("test1", result.get(0).getUser().getName());
    verify(participantRepo, times(1)).findByStatus(CommonTypes.ParticipantStatus.waiting);
  }
  
  @Test
  void testFindByRole() {
    List<Participant> participants = Arrays.asList(participant, participant2);
    when(participantRepo.findByRole(CommonTypes.Role.organizer)).thenReturn(participants);
    
    List<Participant> result = participantService.findByRole(CommonTypes.Role.organizer);
    
    //    assertEquals(1, result.size());
    assertEquals(CommonTypes.Role.organizer, result.get(0).getRole());
    verify(participantRepo, times(1)).findByRole(CommonTypes.Role.organizer);
  }
  
  @Test
  void testSaveUser() {
    participantService.save(participant);
    verify(participantRepo, times(1)).save(participant);
  }
}

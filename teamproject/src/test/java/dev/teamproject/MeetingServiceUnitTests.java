package dev.teamproject;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import dev.teamproject.common.CommonTypes;
import dev.teamproject.meeting.Meeting;
import dev.teamproject.meeting.MeetingRepo;
import dev.teamproject.meeting.MeetingService;
import dev.teamproject.user.User;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

/** The type Meeting service unit tests. */
public class MeetingServiceUnitTests {
  @Mock private MeetingRepo meetingRepo;
  @InjectMocks private MeetingService meetingService;

  private User user1;
  private User user2;

  private Meeting meeting1;
  private Meeting meeting2;
  private Meeting meeting3;

  private List<Meeting> allMeetingsDesc;

  /** Sets up. */
  @BeforeEach
  public void setUp() {
    MockitoAnnotations.openMocks(this);
    user1 = new User("test", "test@columbia.edu");
    user2 = new User("test2", "tes2t@columbia.edu");
    meeting1 = new Meeting();
    meeting2 = new Meeting();
    meeting3 = new Meeting();

    // setup meeting1
    meeting1.setOrganizer(user1);
    meeting1.setMid(0);
    meeting1.setRecurrence(CommonTypes.Recurrence.weekly);
    // setup meeting2
    meeting2.setOrganizer(user2);
    meeting2.setMid(1);
    meeting2.setRecurrence(CommonTypes.Recurrence.daily);
    // setup meeting3
    meeting3.setOrganizer(user2);
    meeting3.setMid(2);
    meeting3.setRecurrence(CommonTypes.Recurrence.daily);

    allMeetingsDesc = Arrays.asList(meeting3, meeting2, meeting1);
  }

  @Test
  public void testFindAll() {
    when(meetingRepo.findAllByOrderByMidDesc()).thenReturn(this.allMeetingsDesc);
    List<Meeting> meetingList = meetingService.findAll();
    assertEquals(3, meetingList.size());
    assertEquals(allMeetingsDesc, meetingList);
  }

  @Test
  public void testFindById() {
    when(meetingRepo.findById(0)).thenReturn(Optional.of(meeting1));
    Meeting foundMeeting = meetingService.findById(0);
    assertEquals(0, foundMeeting.getMid());
  }

  @Test
  public void testFindByOrganizerSingle() {
    List<Meeting> meetingsByOrganizer = Arrays.asList(meeting1);
    when(meetingRepo.findByOrganizer(user1)).thenReturn(meetingsByOrganizer);
    List<Meeting> result = meetingService.findByOrganizer(user1);
    assertEquals(1, result.size());
    assertEquals(user1, result.get(0).getOrganizer());
  }

  @Test
  public void testFindByRecurrence() {
    CommonTypes.Recurrence recurrence = CommonTypes.Recurrence.daily;
    List<Meeting> meetingsByRecurrence =
        Arrays.asList(allMeetingsDesc.get(0)); // return meeting2, daily
    when(meetingRepo.findByRecurrence(recurrence)).thenReturn(meetingsByRecurrence);
    List<Meeting> result = meetingService.findByRecurrence(recurrence);
    assertEquals(1, result.size());
    assertEquals(recurrence, result.get(0).getRecurrence());
  }

  @Test
  public void testFindByStatus() {
    CommonTypes.MeetingStatus status = CommonTypes.MeetingStatus.Valid;
    List<Meeting> meetingsByStatus = Arrays.asList(allMeetingsDesc.get(0));
    when(meetingRepo.findByStatus(status)).thenReturn(meetingsByStatus);
    List<Meeting> result = meetingService.findByStatus(status);
    assertEquals(1, result.size());
  }

  @Test
  public void testFindByType() {
    CommonTypes.MeetingType type = CommonTypes.MeetingType.group;
    List<Meeting> meetingsByType = Arrays.asList(allMeetingsDesc.get(0));
    when(meetingRepo.findByType(type)).thenReturn(meetingsByType);
    List<Meeting> result = meetingService.findByType(type);
    assertEquals(1, result.size());
  }

  @Test
  public void testDeleteMeeting() {
    Meeting meetingToDelete = allMeetingsDesc.get(0);
    meetingService.delete(meetingToDelete);
    verify(meetingRepo, times(1)).delete(meetingToDelete);
  }

  @Test
  public void testSaveMeeting() {
    Meeting meetingToSave = new Meeting();
    meetingService.save(meetingToSave);
    verify(meetingRepo, times(1)).save(meetingToSave);
  }
}

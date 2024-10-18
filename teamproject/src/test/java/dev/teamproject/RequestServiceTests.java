package dev.teamproject;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import dev.teamproject.common.CommonTypes;
import dev.teamproject.request.Request;
import dev.teamproject.request.RequestId;
import dev.teamproject.request.RequestRepo;
import dev.teamproject.request.RequestService;
import dev.teamproject.timeslot.TimeSlot;
import dev.teamproject.timeslot.TimeSlotService;
import dev.teamproject.user.User;
import dev.teamproject.user.UserService;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class RequestServiceTests {

  @InjectMocks
  private RequestService requestService;

  @Mock
  private RequestRepo requestRepo;

  @Mock
  private UserService userService;

  @Mock
  private TimeSlotService timeSlotService;

  private Request request;
  private User user;
  private TimeSlot timeSlot;
  private RequestId requestId;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
    user = new User();

    timeSlot = new TimeSlot();
    timeSlot.setTid(1);

    request = new Request();
    request.setUser(user);
    request.setTimeSlot(timeSlot);

    requestId = new RequestId(timeSlot, user);
  }

  @Test
  void testCreateRequest() {
    when(userService.findById(user.getUid())).thenReturn(user);
    when(timeSlotService.getTimeSlotById(timeSlot.getTid())).thenReturn(timeSlot);
    when(requestRepo.save(request)).thenReturn(request);

    Request createdRequest = requestService.createRequest(request);
    assertEquals(request, createdRequest);
  }

  @Test
  void testDeleteRequest() {
    when(timeSlotService.getTimeSlotById(timeSlot.getTid())).thenReturn(timeSlot);
    when(userService.findById(user.getUid())).thenReturn(user);
    when(requestRepo.findById(requestId)).thenReturn(Optional.of(request));

    requestService.deleteRequest(timeSlot.getTid(), user.getUid());
  }

  @Test
  void testDeleteRequestNotFound() {
    when(timeSlotService.getTimeSlotById(timeSlot.getTid())).thenReturn(timeSlot);
    when(userService.findById(user.getUid())).thenReturn(user);
    when(requestRepo.findById(requestId)).thenReturn(Optional.empty());

    assertThrows(RuntimeException.class, () -> requestService
            .deleteRequest(timeSlot.getTid(), user.getUid()));
  }

  @Test
  void testUpdateRequestDescription() {
    when(timeSlotService.getTimeSlotById(timeSlot.getTid())).thenReturn(timeSlot);
    when(userService.findById(user.getUid())).thenReturn(user);
    when(requestRepo.findById(requestId)).thenReturn(Optional.of(request));
    when(requestRepo.save(request)).thenReturn(request);

    Request updatedRequest = requestService
            .updateRequestDescription(timeSlot.getTid(), user.getUid(), "New Description");
    assertEquals("New Description", updatedRequest.getDescription());
  }

  @Test
  void testUpdateRequestStatus() {
    when(timeSlotService.getTimeSlotById(timeSlot.getTid())).thenReturn(timeSlot);
    when(userService.findById(user.getUid())).thenReturn(user);
    when(requestRepo.findById(requestId)).thenReturn(Optional.of(request));
    when(requestRepo.save(request)).thenReturn(request);

    Request updatedRequest = requestService.updateRequestStatus(timeSlot.getTid(),
            user.getUid(), CommonTypes.RequestStatus.approved);
    assertEquals(CommonTypes.RequestStatus.approved, updatedRequest.getStatus());
  }

  @Test
  void testGetRequestById() {
    when(timeSlotService.getTimeSlotById(timeSlot.getTid())).thenReturn(timeSlot);
    when(userService.findById(user.getUid())).thenReturn(user);
    when(requestRepo.findById(requestId)).thenReturn(Optional.of(request));

    Request foundRequest = requestService.getRequestById(user.getUid(), timeSlot.getTid());
    assertEquals(request, foundRequest);
  }

  @Test
  void testGetRequestsByRequester() {
    when(userService.findById(user.getUid())).thenReturn(user);
    List<Request> requests = Collections.singletonList(request);
    when(requestRepo.findByUser(user)).thenReturn(requests);

    List<Request> foundRequests = requestService.getRequestsByRequester(user.getUid());
    assertEquals(requests, foundRequests);
  }

  @Test
  void testGetRequestsByTimeSlot() {
    when(timeSlotService.getTimeSlotById(timeSlot.getTid())).thenReturn(timeSlot);
    List<Request> requests = Collections.singletonList(request);
    when(requestRepo.findByTimeSlot(timeSlot)).thenReturn(requests);

    List<Request> foundRequests = requestService.getRequestsByTimeSlot(timeSlot.getTid());
    assertEquals(requests, foundRequests);
  }
}

package dev.teamproject.request;

import dev.teamproject.common.CommonTypes;
import dev.teamproject.timeslot.TimeSlot;
import dev.teamproject.timeslot.TimeSlotService;
import dev.teamproject.user.User;
import dev.teamproject.user.UserService;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service class for managing requests associated with time slots.
 */
@Service
public class RequestService {
  private final RequestRepo requestRepo;
  private final UserService userService;
  private final TimeSlotService tsService;
  private final Object lock = new Object();

  @Autowired
  public RequestService(RequestRepo requestRepo,
                        UserService userService, TimeSlotService tsService) {
    this.tsService = tsService;
    this.userService = userService;
    this.requestRepo = requestRepo;
  }

  /**
   * Creates a new request.
   *
   * @param req the request to be created
   * @return the created request
   */
  @Transactional
  public Request createRequest(Request req) {
    synchronized (lock) {
      User user = userService.findById(req.getUser().getUid());
      req.setUser(user);
      TimeSlot ts = tsService.getTimeSlotById(req.getTimeSlot().getTid());
      req.setTimeSlot(ts);
      return requestRepo.save(req);
    }
  }

  /**
   * Deletes a request by its time slot ID and requester ID.
   *
   * @param tid        the time slot ID
   * @param requesterId the ID of the requester
   * @throws RuntimeException if the request is not found
   */
  @Transactional
  public void deleteRequest(int tid, int requesterId) {
    RequestId requestId = new RequestId(
            tsService.getTimeSlotById(tid), userService.findById(requesterId));
    Optional<Request> request = requestRepo.findById(requestId);
    if (request.isPresent()) {
      requestRepo.deleteById(requestId);
    } else {
      throw new RuntimeException("Request not found");
    }
  }

  /**
   * Updates the description of a request.
   *
   * @param tid             the time slot ID
   * @param requesterId     the ID of the requester
   * @param newDescription  the new description for the request
   * @return the updated request
   * @throws RuntimeException if the request is not found
   */
  @Transactional
  public Request updateRequestDescription(int tid, int requesterId, String newDescription) {
    RequestId requestId = new RequestId(
            tsService.getTimeSlotById(tid), userService.findById(requesterId));
    synchronized (lock) {
      Optional<Request> optionalRequest = requestRepo.findById(requestId);
      if (optionalRequest.isPresent()) {
        Request request = optionalRequest.get();
        if (newDescription != null && !newDescription.isEmpty()) {
          request.setDescription(newDescription);
        }
        return requestRepo.save(request);
      } else {
        throw new RuntimeException("Request not found");
      }
    }
  }

  /**
   * Updates the status of a request.
   *
   * @param tid        the time slot ID
   * @param requesterId the ID of the requester
   * @param status     the new status for the request
   * @return the updated request
   * @throws RuntimeException if the request is not found
   */
  @Transactional
  public Request updateRequestStatus(int tid, int requesterId, CommonTypes.RequestStatus status) {
    RequestId requestId = new RequestId(tsService.getTimeSlotById(tid),
            userService.findById(requesterId));
    synchronized (lock) {
      Optional<Request> optionalRequest = requestRepo.findById(requestId);
      if (optionalRequest.isPresent()) {
        Request request = optionalRequest.get();
        if (status != null) {
          request.setStatus(status);
        }
        return requestRepo.save(request);
      } else {
        throw new RuntimeException("Request not found");
      }
    }
  }

  /**
   * Retrieves a request by its requester ID and time slot ID.
   *
   * @param requesterId the ID of the requester
   * @param tid        the time slot ID
   * @return the found request
   * @throws RuntimeException if the request is not found
   */
  @Transactional(readOnly = true)
  public Request getRequestById(int requesterId, int tid) {
    RequestId requestId = new RequestId(tsService.getTimeSlotById(tid),
            userService.findById(requesterId));
    Optional<Request> optionalRequest = requestRepo.findById(requestId);
    if (optionalRequest.isPresent()) {
      return optionalRequest.get();
    } else {
      throw new RuntimeException("Request not found");
    }
  }

  /**
   * Retrieves a list of requests by the requester ID.
   *
   * @param uid the ID of the user making the requests
   * @return a list of requests associated with the user
   */
  @Transactional(readOnly = true)
  public List<Request> getRequestsByRequester(int uid) {
    User user = userService.findById(uid);
    return requestRepo.findByUser(user);
  }

  /**
   * Retrieves a list of requests associated with a specific time slot.
   *
   * @param tid the time slot ID
   * @return a list of requests for the specified time slot
   */
  @Transactional(readOnly = true)
  public List<Request> getRequestsByTimeSlot(int tid) {
    TimeSlot ts = tsService.getTimeSlotById(tid);
    return requestRepo.findByTimeSlot(ts);
  }
}

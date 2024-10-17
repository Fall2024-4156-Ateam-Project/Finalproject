package dev.teamproject.request;

import dev.teamproject.common.CommonTypes;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


/**
 * The RequestController class handles incoming HTTP requests related to the Request entity.
 * Provides RESTful endpoints for creating, retrieving, updating, and deleting requests.
 */

@RestController
@RequestMapping("/api/v1/requests")
public class RequestController {
  private final RequestService requestService;
  
  @Autowired
  public RequestController(RequestService requestService) {
    this.requestService = requestService;
  }
  
  // Create a new one
  @PostMapping
  public ResponseEntity<Request> createRequest(@RequestBody Request request) {
    Request req = requestService.createRequest(request);
    return ResponseEntity.ok(req);
  }
  
  // Get by ID
  @GetMapping("/{userid}/{tid}")
  public ResponseEntity<Request> getRequestById(@PathVariable("userid") int requesterId,
                                                @PathVariable("tid") int tid) {
    Request timeSlot = requestService.getRequestById(requesterId, tid);
    return ResponseEntity.ok(timeSlot);
  }

  /**
   * Searches for requests by either user ID or time slot ID.
   *
   * @param requesterId the ID of the user making the request (optional).
   * @param tid the ID of the time slot associated with the request (optional).
   * @return a ResponseEntity containing a list of matching requests or an error
   *         message if neither parameter is provided.
   */

  @GetMapping("/search")
  public ResponseEntity<?> getRequests(
      @RequestParam(required = false) Integer requesterId,
      @RequestParam(required = false) Integer tid) {
    
    if (requesterId != null) {
      // Get requests by UID only
      List<Request> requests = requestService.getRequestsByRequester(requesterId);
      return ResponseEntity.ok(requests);
    } else if (tid != null) {
      // Get requests by TID only
      List<Request> requests = requestService.getRequestsByTimeSlot(tid);
      return ResponseEntity.ok(requests);
    } else {
      return ResponseEntity.badRequest().body(
              "Invalid request: Please provide either 'userid' or 'tid'");
    }
  }
  
  // Update
  @PutMapping("/description")
  public ResponseEntity<Request> updateRequestDescription(@RequestParam("userid") int requesterId,
                                                          @RequestParam("tid") int tid,
                                                          @RequestBody String description) {
    Request req = requestService.updateRequestDescription(tid, requesterId, description);
    return ResponseEntity.ok(req);
  }
  
  @PutMapping("/status")
  public ResponseEntity<Request> updateRequestStatus(
          @RequestParam("userid") int requesterId,
          @RequestParam("tid") int tid,
          @RequestBody CommonTypes.RequestStatus status) {
    Request req = requestService.updateRequestStatus(tid, requesterId, status);
    return ResponseEntity.ok(req);
  }
  
  // Delete
  @DeleteMapping()
  public ResponseEntity<Void> deleteRequest(@RequestParam("userid") int requesterId,
                                            @RequestParam("tid") int tid) {
    requestService.deleteRequest(tid, requesterId);
    return ResponseEntity.noContent().build();
  }
  
  
}

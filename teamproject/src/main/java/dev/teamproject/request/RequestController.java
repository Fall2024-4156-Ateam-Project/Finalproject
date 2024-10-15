package dev.teamproject.request;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import dev.teamproject.common.commonTypes;

import java.util.List;

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
            return ResponseEntity.badRequest().body("Invalid request: Please provide either 'userid' or 'tid'");
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
    public ResponseEntity<Request> updateRequestStatus(@RequestParam("userid") int requesterId,
                                                @RequestParam("tid") int tid, 
                                                @RequestBody commonTypes.RequestStatus status) {
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

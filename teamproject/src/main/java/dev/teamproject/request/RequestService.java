package dev.teamproject.request;

import dev.teamproject.timeslot.TimeSlot;
import dev.teamproject.timeslot.TimeSlotService;
import dev.teamproject.user.User;
import dev.teamproject.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import dev.teamproject.common.commonTypes;

import java.util.List;
import java.util.Optional;

@Service
public class RequestService {
    private final RequestRepo requestRepo;
    private final UserService userService;
    private final TimeSlotService tsService;

    @Autowired
    public RequestService(RequestRepo requestRepo, UserService userService, TimeSlotService tsService) {
        this.tsService = tsService;
        this.userService = userService;
        this.requestRepo = requestRepo;
    }

    // Create
    public Request createRequest(Request req) {
        // Validate user exists
        User user = userService.findById(req.getUser().getUid());
        req.setUser(user);
        TimeSlot ts = tsService.getTimeSlotById(req.getTimeSlot().getTid());
        req.setTimeSlot(ts);
        return requestRepo.save(req);
    }

    public void deleteRequest(int tid, int requesterId) {
        RequestId requestId = new RequestId(tsService.getTimeSlotById(tid), userService.findById(requesterId));  
        Optional<Request> request = requestRepo.findById(requestId); 

        if (request.isPresent()) {
            requestRepo.deleteById(requestId);
        } else {
            throw new RuntimeException("Request not found");
        }
    }

    public Request updateRequestDescription(int tid, int requesterId, String newDescription) {
        RequestId requestId = new RequestId(tsService.getTimeSlotById(tid), userService.findById(requesterId)); 
        Optional<Request> optionalRequest = requestRepo.findById(requestId);

        if (optionalRequest.isPresent()) {
            Request request = optionalRequest.get();
            // Update description if newDescription is not null or not empty
            if (newDescription != null && !newDescription.isEmpty()) {
                request.setDescription(newDescription);
            }

            return requestRepo.save(request);  
        } else {
            throw new RuntimeException("Request not found");  
        }
    }

    public Request updateRequestStatus(int tid, int requesterId,  commonTypes.RequestStatus status) {
        RequestId requestId = new RequestId(tsService.getTimeSlotById(tid), userService.findById(requesterId)); 
        Optional<Request> optionalRequest = requestRepo.findById(requestId);

        if (optionalRequest.isPresent()) {
            Request request = optionalRequest.get();
            // Update description if newDescription is not null or not empty
            if (status != null) {
                request.setStatus(status);
            }

            return requestRepo.save(request);  
        } else {
            throw new RuntimeException("Request not found");  
        }
    }

    public Request getRequestById(int requesterId, int tid) {
        RequestId requestId = new RequestId(tsService.getTimeSlotById(tid), userService.findById(requesterId));  // Create the composite key
        Optional<Request> optionalRequest = requestRepo.findById(requestId);

        if (optionalRequest.isPresent()) {
            return optionalRequest.get();  // Return the found Request
        } else {
            throw new RuntimeException("Request not found");  // Handle request not found
        }
    }

    public List<Request> getRequestsByRequester(int uid) {
        User user = userService.findById(uid);
        return requestRepo.findByUser(user);
    }

    public List<Request> getRequestsByTimeSlot(int tid) {
        TimeSlot ts = tsService.getTimeSlotById(tid);
        return requestRepo.findByTimeSlot(ts);
    }
    
    
}

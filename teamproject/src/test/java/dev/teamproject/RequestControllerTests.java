package dev.teamproject;

import dev.teamproject.common.CommonTypes;
import dev.teamproject.request.Request;
import dev.teamproject.request.RequestController;
import dev.teamproject.request.RequestId;
import dev.teamproject.request.RequestService;
import dev.teamproject.timeslot.TimeSlot;
import dev.teamproject.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class RequestControllerTests {

    @InjectMocks
    private RequestController requestController;

    @Mock
    private RequestService requestService;

    private Request request;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        request = new Request();
    }

    @Test
    void testCreateRequest() {
        when(requestService.createRequest(request)).thenReturn(request);
        ResponseEntity<Request> response = requestController.createRequest(request);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(request, response.getBody());
    }

    @Test
    void testGetRequestById() {
        int requesterId = 1;
        int tid = 1;
        when(requestService.getRequestById(requesterId, tid)).thenReturn(request);
        ResponseEntity<Request> response = requestController.getRequestById(requesterId, tid);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(request, response.getBody());
    }

    @Test
    void testGetRequestsByRequester() {
        List<Request> requests = Arrays.asList(request);
        when(requestService.getRequestsByRequester(1)).thenReturn(requests);
        ResponseEntity<?> response = requestController.getRequests(1, null);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(requests, response.getBody());
    }

    @Test
    void testGetRequestsByTimeSlot() {
        List<Request> requests = Arrays.asList(request);
        when(requestService.getRequestsByTimeSlot(1)).thenReturn(requests);
        ResponseEntity<?> response = requestController.getRequests(null, 1);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(requests, response.getBody());
    }

    @Test
    void testUpdateRequestDescription() {
        when(requestService.updateRequestDescription(1, 1, "New Description")).thenReturn(request);
        ResponseEntity<Request> response = requestController.updateRequestDescription(1, 1, "New Description");
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(request, response.getBody());
    }

    @Test
    void testUpdateRequestStatus() {
        when(requestService.updateRequestStatus(1, 1, CommonTypes.RequestStatus.approved)).thenReturn(request);
        ResponseEntity<Request> response = requestController.updateRequestStatus(1, 1, CommonTypes.RequestStatus.approved);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(request, response.getBody());
    }

    @Test
    void testDeleteRequest() {
        ResponseEntity<Void> response = requestController.deleteRequest(1, 1);
        assertEquals(204, response.getStatusCodeValue());
    }
}

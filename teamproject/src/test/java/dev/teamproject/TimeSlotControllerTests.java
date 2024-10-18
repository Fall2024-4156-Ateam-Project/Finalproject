package dev.teamproject;

import dev.teamproject.common.CommonTypes;
import dev.teamproject.timeslot.TimeSlot;
import dev.teamproject.timeslot.TimeSlotController;
import dev.teamproject.timeslot.TimeSlotService;
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

class TimeSlotControllerTests {

    @InjectMocks
    private TimeSlotController timeSlotController;

    @Mock
    private TimeSlotService timeSlotService;

    private TimeSlot timeSlot;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        timeSlot = new TimeSlot();
        timeSlot.setTid(1);
    }

    @Test
    void testCreateTimeSlot() {
        when(timeSlotService.createTimeSlot(timeSlot)).thenReturn(timeSlot);
        ResponseEntity<TimeSlot> response = timeSlotController.createTimeSlot(timeSlot);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(timeSlot, response.getBody());
    }

    @Test
    void testGetTimeSlotById() {
        int tid = 1;
        when(timeSlotService.getTimeSlotById(tid)).thenReturn(timeSlot);
        ResponseEntity<TimeSlot> response = timeSlotController.getTimeSlotById(tid);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(timeSlot, response.getBody());
    }

    @Test
    void testGetAllTimeSlots() {
        List<TimeSlot> timeSlots = Arrays.asList(timeSlot);
        when(timeSlotService.getAllTimeSlots()).thenReturn(timeSlots);
        ResponseEntity<List<TimeSlot>> response = timeSlotController.getAllTimeSlots();
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(timeSlots, response.getBody());
    }

    @Test
    void testGetTimeSlotsByUser() {
        int uid = 1;
        List<TimeSlot> timeSlots = Arrays.asList(timeSlot);
        when(timeSlotService.getTimeSlotsByUser(uid)).thenReturn(timeSlots);
        ResponseEntity<List<TimeSlot>> response = timeSlotController.getTimeSlotsByUser(uid);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(timeSlots, response.getBody());
    }

    @Test
    void testGetTimeSlotsByDay() {
        CommonTypes.Day day = CommonTypes.Day.Monday;
        List<TimeSlot> timeSlots = Arrays.asList(timeSlot);
        when(timeSlotService.getTimeSlotsByDay(day)).thenReturn(timeSlots);
        ResponseEntity<List<TimeSlot>> response = timeSlotController.getTimeSlotsByDay(day);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(timeSlots, response.getBody());
    }

    @Test
    void testGetTimeSlotsByAvailability() {
        CommonTypes.Availability availability = CommonTypes.Availability.available;
        List<TimeSlot> timeSlots = Arrays.asList(timeSlot);
        when(timeSlotService.getTimeSlotsByAvailability(availability)).thenReturn(timeSlots);
        ResponseEntity<List<TimeSlot>> response = timeSlotController.getTimeSlotsByAvailability(availability);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(timeSlots, response.getBody());
    }

    @Test
    void testUpdateTimeSlot() {
        int tid = 1;
        when(timeSlotService.updateTimeSlot(tid, timeSlot)).thenReturn(timeSlot);
        ResponseEntity<TimeSlot> response = timeSlotController.updateTimeSlot(tid, timeSlot);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(timeSlot, response.getBody());
    }

    @Test
    void testDeleteTimeSlot() {
        int tid = 1;
        ResponseEntity<Void> response = timeSlotController.deleteTimeSlot(tid);
        assertEquals(204, response.getStatusCodeValue());
    }
}

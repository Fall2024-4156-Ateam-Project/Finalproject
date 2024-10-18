package dev.teamproject;

import dev.teamproject.common.CommonTypes;
import dev.teamproject.timeslot.TimeSlot;
import dev.teamproject.timeslot.TimeSlotRepo;
import dev.teamproject.timeslot.TimeSlotService;
import dev.teamproject.user.User;
import dev.teamproject.user.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

class TimeSlotServiceTests {

    @InjectMocks
    private TimeSlotService timeSlotService;

    @Mock
    private TimeSlotRepo timeSlotRepo;

    @Mock
    private UserService userService;

    private TimeSlot timeSlot;
    private User user;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        user = new User();
        timeSlot = new TimeSlot();
        timeSlot.setTid(1);
        timeSlot.setUser(user);
    }

    @Test
    void testCreateTimeSlot() {
        when(userService.findById(user.getUid())).thenReturn(user);
        when(timeSlotRepo.save(timeSlot)).thenReturn(timeSlot);
        TimeSlot createdTimeSlot = timeSlotService.createTimeSlot(timeSlot);
        assertEquals(timeSlot, createdTimeSlot);
    }

    @Test
    void testGetTimeSlotById() {
        int tid = 1;
        when(timeSlotRepo.findById(tid)).thenReturn(Optional.of(timeSlot));
        TimeSlot retrievedTimeSlot = timeSlotService.getTimeSlotById(tid);
        assertEquals(timeSlot, retrievedTimeSlot);
    }

    @Test
    void testGetAllTimeSlots() {
        List<TimeSlot> timeSlots = Arrays.asList(timeSlot);
        when(timeSlotRepo.findAll()).thenReturn(timeSlots);
        List<TimeSlot> retrievedTimeSlots = timeSlotService.getAllTimeSlots();
        assertEquals(timeSlots, retrievedTimeSlots);
    }

    @Test
    void testGetTimeSlotsByUser() {
        when(userService.findById(user.getUid())).thenReturn(user);
        List<TimeSlot> timeSlots = Arrays.asList(timeSlot);
        when(timeSlotRepo.findByUser(user)).thenReturn(timeSlots);
        List<TimeSlot> retrievedTimeSlots = timeSlotService.getTimeSlotsByUser(user.getUid());
        assertEquals(timeSlots, retrievedTimeSlots);
    }

    @Test
    void testGetTimeSlotsByDay() {
        CommonTypes.Day day = CommonTypes.Day.Monday;
        List<TimeSlot> timeSlots = Arrays.asList(timeSlot);
        when(timeSlotRepo.findByDay(day)).thenReturn(timeSlots);
        List<TimeSlot> retrievedTimeSlots = timeSlotService.getTimeSlotsByDay(day);
        assertEquals(timeSlots, retrievedTimeSlots);
    }

    @Test
    void testGetTimeSlotsByAvailability() {
        CommonTypes.Availability availability = CommonTypes.Availability.available;
        List<TimeSlot> timeSlots = Arrays.asList(timeSlot);
        when(timeSlotRepo.findByAvailability(availability)).thenReturn(timeSlots);
        List<TimeSlot> retrievedTimeSlots = timeSlotService.getTimeSlotsByAvailability(availability);
        assertEquals(timeSlots, retrievedTimeSlots);
    }

    @Test
    void testUpdateTimeSlot() {
        int tid = 1;
        when(timeSlotRepo.findById(tid)).thenReturn(Optional.of(timeSlot));
        when(timeSlotRepo.save(timeSlot)).thenReturn(timeSlot);

        TimeSlot updatedTimeSlot = timeSlotService.updateTimeSlot(tid, timeSlot);
        assertEquals(timeSlot, updatedTimeSlot);
    }

    @Test
    void testDeleteTimeSlot() {
        int tid = 1;
        when(timeSlotRepo.existsById(tid)).thenReturn(true);
        timeSlotService.deleteTimeSlot(tid);
    }

    @Test
    void testDeleteTimeSlotNotFound() {
        int tid = 1;
        when(timeSlotRepo.existsById(tid)).thenReturn(false);
        assertThrows(RuntimeException.class, () -> timeSlotService.deleteTimeSlot(tid));
    }
}

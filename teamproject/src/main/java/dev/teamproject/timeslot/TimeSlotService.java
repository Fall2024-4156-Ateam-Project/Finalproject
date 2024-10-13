package dev.teamproject.timeslot;

import dev.teamproject.common.commonTypes;
import dev.teamproject.user.User;
import dev.teamproject.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TimeSlotService {
    private final TimeSlotRepo timeSlotRepo;
    private final UserService userService;

    @Autowired
    public TimeSlotService(TimeSlotRepo timeSlotRepo, UserService userService) {
        this.timeSlotRepo = timeSlotRepo;
        this.userService = userService;
    }

    // Create
    public TimeSlot createTimeSlot(TimeSlot timeSlot) {
        // Validate user exists
        User user = userService.findById(timeSlot.getUser().getUid());
        timeSlot.setUser(user);
        return timeSlotRepo.save(timeSlot);
    }

    // Read
    public TimeSlot getTimeSlotById(int tid) {
        return timeSlotRepo.findById(tid)
                .orElseThrow(() -> new RuntimeException("TimeSlot not found with id: " + tid));
    }

    public List<TimeSlot> getAllTimeSlots() {
        return timeSlotRepo.findAll();
    }

    public List<TimeSlot> getTimeSlotsByUser(int uid) {
        User user = userService.findById(uid);
        return timeSlotRepo.findByUser(user);
    }

    public List<TimeSlot> getTimeSlotsByDay(commonTypes.Day day) {
        return timeSlotRepo.findByDay(day);
    }

    public List<TimeSlot> getTimeSlotsByAvailability(commonTypes.Availability availability) {
        return timeSlotRepo.findByAvailability(availability);
    }

    // Update
    public TimeSlot updateTimeSlot(int tid, TimeSlot updatedTimeSlot) {
        TimeSlot existingTimeSlot = getTimeSlotById(tid);
        existingTimeSlot.setUser(updatedTimeSlot.getUser());
        existingTimeSlot.setDay(updatedTimeSlot.getDay());
        existingTimeSlot.setStartTime(updatedTimeSlot.getStartTime());
        existingTimeSlot.setEndTime(updatedTimeSlot.getEndTime());
        existingTimeSlot.setAvailability(updatedTimeSlot.getAvailability());
        return timeSlotRepo.save(existingTimeSlot);
    }

    // Delete
    public void deleteTimeSlot(int tid) {
        if (!timeSlotRepo.existsById(tid)) {
            throw new RuntimeException("TimeSlot not found with id: " + tid);
        }
        timeSlotRepo.deleteById(tid);
    }
}

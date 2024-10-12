package dev.teamproject.timeslot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TimeSlotService {
    private final TimeSlotRepo timeSlotRepo;
    @Autowired
    public TimeSlotService(TimeSlotRepo timeSlotRepo) {
        this.timeSlotRepo = timeSlotRepo;
    }
    public TimeSlot findById(int tid) {
        return this.timeSlotRepo.findById(tid).get();
    }
}

package dev.teamproject.timeslot;

import dev.teamproject.common.CommonTypes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/timeslots")
public class TimeSlotController {
  
  private final TimeSlotService timeSlotService;
  
  @Autowired
  public TimeSlotController(TimeSlotService timeSlotService) {
    this.timeSlotService = timeSlotService;
  }
  
  // Create a new TimeSlot
  @PostMapping
  public ResponseEntity<TimeSlot> createTimeSlot(@RequestBody TimeSlot timeSlot) {
    TimeSlot createdTimeSlot = timeSlotService.createTimeSlot(timeSlot);
    return ResponseEntity.ok(createdTimeSlot);
  }
  
  // Get TimeSlot by ID
  @GetMapping("/{id}")
  public ResponseEntity<TimeSlot> getTimeSlotById(@PathVariable("id") int tid) {
    TimeSlot timeSlot = timeSlotService.getTimeSlotById(tid);
    return ResponseEntity.ok(timeSlot);
  }
  
  // Get all TimeSlots
  @GetMapping
  public ResponseEntity<List<TimeSlot>> getAllTimeSlots() {
    List<TimeSlot> timeSlots = timeSlotService.getAllTimeSlots();
    return ResponseEntity.ok(timeSlots);
  }
  
  // Get TimeSlots by User ID
  @GetMapping("/user/{uid}")
  public ResponseEntity<List<TimeSlot>> getTimeSlotsByUser(@PathVariable("uid") int uid) {
    List<TimeSlot> timeSlots = timeSlotService.getTimeSlotsByUser(uid);
    return ResponseEntity.ok(timeSlots);
  }
  
  // Get TimeSlots by Day
  @GetMapping("/day/{day}")
  public ResponseEntity<List<TimeSlot>> getTimeSlotsByDay(@PathVariable("day") CommonTypes.Day day) {
    List<TimeSlot> timeSlots = timeSlotService.getTimeSlotsByDay(day);
    return ResponseEntity.ok(timeSlots);
  }
  
  // Get TimeSlots by Availability
  @GetMapping("/availability/{availability}")
  public ResponseEntity<List<TimeSlot>> getTimeSlotsByAvailability(@PathVariable("availability") CommonTypes.Availability availability) {
    List<TimeSlot> timeSlots = timeSlotService.getTimeSlotsByAvailability(availability);
    return ResponseEntity.ok(timeSlots);
  }
  
  // Update a TimeSlot
  @PutMapping("/{id}")
  public ResponseEntity<TimeSlot> updateTimeSlot(@PathVariable("id") int tid, @RequestBody TimeSlot timeSlot) {
    TimeSlot updatedTimeSlot = timeSlotService.updateTimeSlot(tid, timeSlot);
    return ResponseEntity.ok(updatedTimeSlot);
  }
  
  // Delete a TimeSlot
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteTimeSlot(@PathVariable("id") int tid) {
    timeSlotService.deleteTimeSlot(tid);
    return ResponseEntity.noContent().build();
  }
}

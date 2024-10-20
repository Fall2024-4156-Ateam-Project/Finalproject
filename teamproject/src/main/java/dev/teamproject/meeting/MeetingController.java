package dev.teamproject.meeting;


import dev.teamproject.common.CommonTypes;
import dev.teamproject.user.User;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
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
 * Handles incoming requests related to meetings, providing API endpoints
 * for managing meeting records in the application.
 */

@RestController
@RequestMapping(path = "api/v1/meetings")
public class MeetingController {
  private final MeetingService meetingService;
  
  @Autowired
  public MeetingController(MeetingService meetingService) {
    this.meetingService = meetingService;
  }
  
  /**
   * Retrieves meetings for the specified recurrence type.
   *
   * @param recurrence the recurrence type of the meetings to find,
   *                   (e.g., "daily", "weekly").
   * @return a list of meetings that match the specified recurrence type.
   */
  
  @GetMapping("/findByRecurrence")
  public List<Meeting> findByRecurrence(@RequestParam("recurrence") String recurrence) {
    //TODO: should check if valid param
    CommonTypes.Recurrence rec = CommonTypes.Recurrence.valueOf(recurrence.toUpperCase());
    return meetingService.findByRecurrence(rec);
  }
  
  /**
   * Retrieves meetings for the specified status.
   *
   * @param status the status of the meetings to find,
   *                   (e.g., "valid", "invalid").
   * @return a list of meetings that match the specified recurrence type.
   */
  
  @GetMapping("/findByStatus")
  public List<Meeting> findByStatus(@RequestParam("status") String status) {
    //TODO: should check if valid param
    String upperStatus = status.toUpperCase();
    CommonTypes.MeetingStatus meetingStatus = CommonTypes.MeetingStatus.valueOf(upperStatus);
    return meetingService.findByStatus(meetingStatus);
  }
  
  /**
   * Retrieves meetings for the specified type.
   *
   * @param type the type of the meetings to find,
   *                   (e.g., "group", "one-on-one").
   * @return a list of meetings that match the specified recurrence type.
   */
  
  @GetMapping("/findByType")
  public List<Meeting> findByType(@RequestParam("type") String type) {
    //TODO: shoud check if valid param
    CommonTypes.MeetingType meetingType = CommonTypes.MeetingType.valueOf(type.toUpperCase());
    return meetingService.findByType(meetingType);
  }
  
  @GetMapping("/findById")
  public Meeting findByid(@RequestParam("id") int mid) {
    return meetingService.findById(mid);
  }
  
  
  @GetMapping("/get_all")
  public List<Meeting> findAll() {
    return meetingService.findAll();
  }
  
  @GetMapping("/findByOrganizer")
  public List<Meeting> findByOrganizer(@RequestBody User organizer) {
    return meetingService.findByOrganizer(organizer);
  }

  @DeleteMapping("/delete")
  public void deleteMeeting(@RequestParam("Id") int mid) {
    meetingService.deleteMeeting(mid);
  }

  @PostMapping("/saveMeeting")
  public void saveMeeting(@RequestBody Meeting meeting) {
    meetingService.save(meeting);
  }
  
}
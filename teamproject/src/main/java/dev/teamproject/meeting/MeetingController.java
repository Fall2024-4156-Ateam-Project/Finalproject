package dev.teamproject.meeting;

import dev.teamproject.common.CommonTypes;
import dev.teamproject.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

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
  public ResponseEntity<List<MeetingDTO>> findByRecurrence(@RequestParam("recurrence") String recurrence) {
    try {
      CommonTypes.Recurrence rec = CommonTypes.Recurrence.valueOf(recurrence);
      List<MeetingDTO> meetings = meetingService.findByRecurrence(rec);
      return ResponseEntity.ok(meetings);
    } catch (IllegalArgumentException e) {
      return ResponseEntity.badRequest().body(null);
    }
  }

  /**
   * Retrieves meetings for the specified status.
   *
   * @param status the status of the meetings to find,
   *               (e.g., "valid", "invalid").
   * @return a list of meetings that match the specified status.
   */

  @GetMapping("/findByStatus")
  public ResponseEntity<List<MeetingDTO>> findByStatus(@RequestParam("status") String status) {
    try {
      CommonTypes.MeetingStatus meetingStatus = CommonTypes.MeetingStatus.valueOf(status);
      List<MeetingDTO> meetings = meetingService.findByStatus(meetingStatus);
      return ResponseEntity.ok(meetings);
    } catch (IllegalArgumentException e) {
      return ResponseEntity.badRequest().body(null);
    }
  }



  /**
   * Retrieves meetings for the specified type.
   *
   * @param type the type of the meetings to find,
   *             (e.g., "group", "one-on-one").
   * @return a list of meetings that match the specified type.
   */

  @GetMapping("/findByType")
  public ResponseEntity<List<MeetingDTO>> findByType(@RequestParam("type") String type) {
    try {
      CommonTypes.MeetingType meetingType = CommonTypes.MeetingType.valueOf(type);
      List<MeetingDTO> meetings = meetingService.findByType(meetingType);
      return ResponseEntity.ok(meetings);
    } catch (IllegalArgumentException e) {
      return ResponseEntity.badRequest().body(null);
    }
  }


  @GetMapping("/findById")
  public ResponseEntity<MeetingDTO> findById(@RequestParam("id") int mid) {
    MeetingDTO meeting = meetingService.findById(mid);
    return meeting != null ? ResponseEntity.ok(meeting) : ResponseEntity.notFound().build();
  }

  @GetMapping("/get_all")
  public ResponseEntity<List<MeetingDTO>> getAllMeetings() {
    List<MeetingDTO> meetings = meetingService.findAll();
    return ResponseEntity.ok(meetings);
  }

  @GetMapping("/findByOrganizer")
  public ResponseEntity<List<MeetingDTO>> findByOrganizer(@RequestBody User organizer) {
    List<MeetingDTO> meetings = meetingService.findByOrganizer(organizer);
    return ResponseEntity.ok(meetings);
  }



  @DeleteMapping()
  public ResponseEntity<Void> deleteMeeting(@RequestParam("mid") int mid) {
    meetingService.deleteMeeting(mid);
    return ResponseEntity.noContent().build();
  }

  @PostMapping("/saveMeeting")
  public ResponseEntity<Void> saveMeeting(@RequestBody MeetingDTO meetingDTO) {
    Meeting meeting = meetingService.convertFromDTO(meetingDTO); // Assuming a constructor exists to convert DTO to Entity
    meetingService.save(meeting);
    return ResponseEntity.created(URI.create("/api/v1/meetings/" + meeting.getMid())).build();
  }
}

package dev.teamproject.participant;

import dev.teamproject.common.CommonTypes;
import dev.teamproject.meeting.Meeting;
import dev.teamproject.user.User;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Handles incoming requests related to participants, providing API endpoints
 * for managing participant records in the application.
 */

@RestController
@RequestMapping(path = "api/v1/participants")
public class ParticipantController {
  private final ParticipantService participantService;
  
  @Autowired
  public ParticipantController(ParticipantService participantService) {
    this.participantService = participantService;
  }
  
  @PostMapping("/register")
  public void saveParticipant(@RequestBody Participant participant) {
    participantService.save(participant);
  }
  
  @PostMapping("/delete")
  public void deleteParticipant(@RequestBody Participant participant) {
    participantService.delete(participant);
  }
  
  @GetMapping("/findById")
  public Participant findById(@RequestParam("Id") int pid) {
    return participantService.findById(pid);
  }
  
  @GetMapping("/findAll")
  public List<Participant> findAll() {
    return participantService.findAll();
  }
  
  @GetMapping("/findByMeeting")
  public List<Participant> findByMeeting(@RequestParam("meeting") Meeting meeting) {
    return participantService.findByMeeting(meeting);
  }
  
  @GetMapping("/findByUser")
  public List<Participant> findByUser(@RequestParam("user") User user) {
    return participantService.findByUser(user);
  }
  
  @GetMapping("/findByStatus")
  public List<Participant> findByStatus(
      @RequestParam("Status") CommonTypes.ParticipantStatus status) {
    //should check if input valid
    return participantService.findByStatus(status);
  }
  
  @GetMapping("/findByRole")
  public List<Participant> findByRole(@RequestParam("Role") CommonTypes.Role role) {
    //should check if input valid
    return participantService.findByRole(role);
  }
  
}
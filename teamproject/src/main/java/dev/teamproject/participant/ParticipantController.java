package dev.teamproject.participant;

import dev.teamproject.common.CommonTypes;
import dev.teamproject.meeting.Meeting;
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
  
  @DeleteMapping("/delete")
  public void deleteParticipant(@RequestParam("Id") int pid) {
    participantService.deleteParticipant(pid);
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

package dev.teamproject.meeting;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import dev.teamproject.common.commonTypes;
import dev.teamproject.user.User;
import java.util.List;

@RestController
@RequestMapping(path = "api/v1/meetings")
public class MeetingController {
    private final MeetingService meetingService;

    @Autowired
    public MeetingController(MeetingService meetingService) {
        this.meetingService = meetingService;
    }

    @GetMapping("/findByRecurrence")
    public List<Meeting> findByRecurrence(@RequestParam("recurrence") String recurrence) {
        //TODO: shoud check if valid param
        commonTypes.Recurrence rec = commonTypes.Recurrence.valueOf(recurrence.toUpperCase());
        return meetingService.findByRecurrence(rec);
    }

    @GetMapping("/findByStatus")
    public List<Meeting> findByStatus(@RequestParam("status") String status) {
        //TODO: should check if valid param
        commonTypes.MeetingStatus meetingStatus = commonTypes.MeetingStatus.valueOf(status.toUpperCase());
        return meetingService.findByStatus(meetingStatus);
    }

    @GetMapping("/findByType")
    public List<Meeting> findByType(@RequestParam("type") String type) {
        //TODO: shoud check if valid param
        commonTypes.MeetingType meetingType = commonTypes.MeetingType.valueOf(type.toUpperCase());
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

    @PostMapping("/deleteMeeting")
    public void deleteMeeting(@RequestBody Meeting meeting){
        meetingService.delete(meeting);
    }

    @PostMapping("/saveMeeting")
    public void saveMeeting(@RequestBody Meeting meeting){
        meetingService.save(meeting);
    }

}
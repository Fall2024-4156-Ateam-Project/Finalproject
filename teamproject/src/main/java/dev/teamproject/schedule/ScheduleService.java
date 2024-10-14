package dev.teamproject.schedule;

import dev.teamproject.common.commonTypes;
import dev.teamproject.meeting.MeetingService;
import dev.teamproject.participant.ParticipantService;
import dev.teamproject.timeslot.TimeSlotService;
import dev.teamproject.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Schedule Service, use multiple other services
 */
@Service
public class ScheduleService {

    private final ParticipantService participantService;
    private final TimeSlotService timeSlotService;
    private final UserService userService;
    private final MeetingService meetingService;

    @Autowired
    public ScheduleService(ParticipantService participantService, TimeSlotService timeSlotService, UserService userService, MeetingService meetingService){
        this.participantService = participantService;
        this.timeSlotService = timeSlotService;
        this.userService = userService;
        this.meetingService = meetingService;
    }

    // Composite service, complex scheduling logics here
}

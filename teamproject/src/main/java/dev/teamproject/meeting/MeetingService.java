package dev.teamproject.meeting;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MeetingService {
    private final MeetingRepo meetingRepo;
    @Autowired
    public MeetingService(MeetingRepo meetingRepo) {
        this.meetingRepo = meetingRepo;
    }
    public Meeting findById(int mid) {
        return meetingRepo.findById(mid).get();
    }
    //TODO: more business logics
}

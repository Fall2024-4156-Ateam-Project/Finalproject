package dev.teamproject.meeting;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import dev.teamproject.common.commonTypes;
import dev.teamproject.user.User;
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

    public List<Meeting> findAll() {
        return meetingRepo.findAllByOrderByMidDesc();
    }

    public List<Meeting> findByOrganizer(User organizer) {
        return meetingRepo.findByOrganizer(organizer);
    }

    public List<Meeting> findByRecurrence(commonTypes.Recurrence recurrence) {
        return meetingRepo.findByRecurrence(recurrence);
    }

    public List<Meeting> findByStatus(commonTypes.MeetingStatus status) {
        return meetingRepo.findByStatus(status);
    }

    public List<Meeting> findByType(commonTypes.MeetingType type) {
        return meetingRepo.findByType(type);
    }

    public void delete(Meeting meeting) {
        meetingRepo.delete(meeting);
    }

    public void save(Meeting meeting) {
        meetingRepo.save(meeting);
    }

}

package dev.teamproject.participant;
import dev.teamproject.user.User;
import dev.teamproject.meeting.Meeting;
import dev.teamproject.common.commonTypes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
@Service
public class ParticipantService {
    private final ParticipantRepo participantRepo;
    @Autowired
    public ParticipantService(ParticipantRepo participantRepo) {
        this.participantRepo = participantRepo;
    }
    public Participant findById(int pid){
        return this.participantRepo.findById(pid);
    }
    //TODO: more business logics

    public List<Participant> findAll(){
        return this.participantRepo.findAllByOrderByPidDesc();
    }

    public List<Participant> findByMeeting(Meeting meeting){
        return this.participantRepo.findByMeeting(meeting);
    }

    public List<Participant> findByUser(User user){
        return this.participantRepo.findByUser(user);
    }

    public List<Participant> findByStatus(commonTypes.ParticipantStatus status){
        return this.participantRepo.findByStatus(status);
    }

    public List<Participant> findByRole(commonTypes.Role role){
        return this.participantRepo.findByRole(role);
    }

    public void save(Participant participant) {
        this.participantRepo.save(participant);
    }

    public void delete(Participant participant) {
        this.participantRepo.delete(participant);
    }


}


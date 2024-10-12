package dev.teamproject.participant;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ParticipantService {
    private final ParticipantRepo participantRepo;
    @Autowired
    public ParticipantService(ParticipantRepo participantRepo) {
        this.participantRepo = participantRepo;
    }
    public Participant findById(int pid){
        return this.participantRepo.findById(pid).get();
    }
    //TODO: more business logics
}

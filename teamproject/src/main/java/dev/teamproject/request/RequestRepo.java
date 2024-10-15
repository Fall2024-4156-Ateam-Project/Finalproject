package dev.teamproject.request;

import dev.teamproject.user.User;
import dev.teamproject.timeslot.TimeSlot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RequestRepo extends JpaRepository<Request, RequestId> {
    List<Request> findByUser(User user);
    List<Request> findByTimeSlot(TimeSlot ts);
}

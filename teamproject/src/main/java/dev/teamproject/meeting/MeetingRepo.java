package dev.teamproject.meeting;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MeetingRepo extends JpaRepository<Meeting, Integer> {
}

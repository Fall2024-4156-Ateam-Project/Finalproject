package dev.teamproject;
import org.junit.jupiter.api.Test;

import dev.teamproject.common.CommonTypes;
import dev.teamproject.meeting.Meeting;
import dev.teamproject.user.User;
import dev.teamproject.participant.Participant;
import java.sql.Timestamp;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;

public class ParticipantUnitTests {

    private Participant participant;
    private Meeting mockMeeting; 
    private User mockUser;

    @BeforeEach
    void setUp() {
        mockMeeting = new Meeting(); 
        mockUser = new User();       
        participant = new Participant(mockMeeting, mockUser, CommonTypes.Role.organizer, CommonTypes.ParticipantStatus.waiting);
    }

    @Test
    void testSetPid() {
        participant.setPid(1);
        assertEquals(1, participant.getPid(), "PID should be set correctly.");
    }

    @Test
    void testSetMeeting() {
        Meeting newMeeting = new Meeting(); 
        participant.setMeeting(newMeeting);
        assertEquals(newMeeting, participant.getMeeting(), "Meeting should be updated correctly.");
    }

    @Test
    void testSetUser() {
        User newUser = new User(); 
        participant.setUser(newUser);
        assertEquals(newUser, participant.getUser(), "User should be updated correctly.");
    }

    @Test
    void testSetJoinAt() {
        participant.setJoinAt(Timestamp.valueOf("2024-01-01 10:00:00"));
        assertEquals(Timestamp.valueOf("2024-01-01 10:00:00"), participant.getJoinAt());
    }

    @Test
    void testGetJoinAt() {
        Timestamp joinAt = participant.getJoinAt();
        assertNotNull(joinAt, "JoinAt timestamp should not be null.");
        assertTrue(joinAt.getTime() <= System.currentTimeMillis(), "JoinAt timestamp should be in the past or present.");
    }
    @Test
    void testParticipantCreation() {
        Meeting meeting = new Meeting(); 
        User user = new User("John Doe", "john@example.com");
        CommonTypes.Role role = CommonTypes.Role.participant; 
        CommonTypes.ParticipantStatus status = CommonTypes.ParticipantStatus.accept;

        Participant participant = new Participant(meeting, user, role, status);

        assertNotNull(participant);
        assertEquals(meeting, participant.getMeeting());
        assertEquals(user, participant.getUser());
        assertEquals(role, participant.getRole());
        assertEquals(status, participant.getStatus());
        assertNotNull(participant.getJoinAt()); 
    }


    @Test
    void testNotEqual() {
        Meeting meeting1 = new Meeting(); 
        Meeting meeting2 = new Meeting(); 
        User user = new User("John Doe", "john@example.com"); 
        CommonTypes.Role role = CommonTypes.Role.participant;
        CommonTypes.ParticipantStatus status = CommonTypes.ParticipantStatus.accept;

        Participant participant1 = new Participant(meeting1, user, role, status);
        participant1.setPid(1); 
        Participant participant2 = new Participant(meeting2, user, role, status); 
        participant2.setPid(2); 

        assertNotEquals(participant1, participant2); 
    }

    @Test
    void testSettersAndGetters() {
        Meeting meeting = new Meeting();
        User user = new User("John Doe", "john@example.com");
        CommonTypes.Role role = CommonTypes.Role.participant;
        CommonTypes.ParticipantStatus status = CommonTypes.ParticipantStatus.accept;

        Participant participant = new Participant(meeting, user, role, status);
        
        participant.setRole(CommonTypes.Role.organizer);
        participant.setStatus(CommonTypes.ParticipantStatus.decline);

        assertEquals(CommonTypes.Role.organizer, participant.getRole());
        assertEquals(CommonTypes.ParticipantStatus.decline, participant.getStatus());
    }
    
}

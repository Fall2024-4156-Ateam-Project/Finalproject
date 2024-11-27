package dev.teamproject.participant;

import dev.teamproject.common.CommonTypes;
import dev.teamproject.meeting.Meeting;
import dev.teamproject.participant.Participant;
import dev.teamproject.participant.ParticipantController;
import dev.teamproject.participant.ParticipantService;
import dev.teamproject.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ParticipantControllerTests {
    private ParticipantController participantController;

    @Mock
    private ParticipantService participantService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        participantController = new ParticipantController(participantService);
    }

    @Test
    void testSaveParticipant() {
        Participant participant = new Participant();
        participantController.saveParticipant(participant);
        verify(participantService, times(1)).save(participant);
    }

    @Test
    void testDeleteParticipant() {
        int pid = 1;
        ResponseEntity<Void> response = participantController.deleteParticipant(pid);
        verify(participantService, times(1)).deleteParticipant(pid);
        assertEquals(ResponseEntity.noContent().build(), response);
    }

    @Test
    void testFindById() {
        int pid = 1;
        Participant participant = new Participant();
        when(participantService.findById(pid)).thenReturn(participant);

        Participant result = participantController.findById(pid);

        verify(participantService, times(1)).findById(pid);
        assertEquals(participant, result);
    }

    @Test
    void testFindAll() {
        List<Participant> participants = Arrays.asList(new Participant(), new Participant());
        when(participantService.findAll()).thenReturn(participants);

        List<Participant> result = participantController.findAll();

        verify(participantService, times(1)).findAll();
        assertEquals(participants, result);
    }

    @Test
    void testFindByMeeting() {
        Meeting meeting = new Meeting();
        List<Participant> participants = Arrays.asList(new Participant(), new Participant());
        when(participantService.findByMeeting(meeting)).thenReturn(participants);

        List<Participant> result = participantController.findByMeeting(meeting);

        verify(participantService, times(1)).findByMeeting(meeting);
        assertEquals(participants, result);
    }

    @Test
    void testFindByUser() {
        User user = new User();
        List<Participant> participants = Arrays.asList(new Participant(), new Participant());
        when(participantService.findByUser(user)).thenReturn(participants);

        List<Participant> result = participantController.findByUser(user);

        verify(participantService, times(1)).findByUser(user);
        assertEquals(participants, result);
    }

    @Test
    void testFindByStatus() {
        CommonTypes.ParticipantStatus status = CommonTypes.ParticipantStatus.accept;
        List<Participant> participants = Arrays.asList(new Participant(), new Participant());
        when(participantService.findByStatus(status)).thenReturn(participants);

        List<Participant> result = participantController.findByStatus(status);

        verify(participantService, times(1)).findByStatus(status);
        assertEquals(participants, result);
    }

    @Test
    void testFindByRole() {
        CommonTypes.Role role = CommonTypes.Role.organizer;
        List<Participant> participants = Arrays.asList(new Participant(), new Participant());
        when(participantService.findByRole(role)).thenReturn(participants);

        List<Participant> result = participantController.findByRole(role);

        verify(participantService, times(1)).findByRole(role);
        assertEquals(participants, result);
    }
}
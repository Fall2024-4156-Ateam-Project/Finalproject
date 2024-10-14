package dev.teamproject.participant;

import dev.teamproject.common.commonTypes;
import dev.teamproject.meeting.Meeting;
import dev.teamproject.user.User;
import jakarta.persistence.*;

import java.sql.Timestamp;

@Entity
@Table(name = "Participant")
public class Participant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PID")
    private int pid;

    @ManyToOne
    @JoinColumn(name = "MID", nullable = false)
    private Meeting meeting;

    @ManyToOne
    @JoinColumn(name = "UID", nullable = false)
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private commonTypes.Role role;

    @Column(name = "join_at")
    private Timestamp joinAt;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private commonTypes.ParticipantStatus status;

    // constructor
    public Participant(){
    }

    public Participant(Meeting meeting, User user, commonTypes.Role role, commonTypes.ParticipantStatus status) {
        this.meeting = meeting;
        this.user = user;
        this.role=role;
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        this.joinAt=timestamp;
        this.status=status;
    }

    // Getters and Setters
    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public Meeting getMeeting() {
        return meeting;
    }

    public void setMeeting(Meeting meeting) {
        this.meeting = meeting;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public commonTypes.Role getRole() {
        return role;
    }

    public void setRole(commonTypes.Role role) {
        this.role = role;
    }

    public Timestamp getJoinAt() {
        return joinAt;
    }

    public void setJoinAt(Timestamp joinAt) {
        this.joinAt = joinAt;
    }

    public commonTypes.ParticipantStatus getStatus() {
        return status;
    }

    public void setStatus(commonTypes.ParticipantStatus status) {
        this.status = status;
    }

}
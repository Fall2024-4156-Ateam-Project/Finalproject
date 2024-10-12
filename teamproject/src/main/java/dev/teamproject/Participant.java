package dev.teamproject;

import dev.teamproject.common;
import dev.teamproject.Meeting;
import dev.teamproject.User;
import jakarta.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

@Entity
@Table(name = "Participant")
public class Participant implements Serializable{
    private static final long serialVersionUID = 1L;

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
    private common.Role role;

    @Column(name = "join_at")
    private Timestamp joinAt;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private common.ParticipantStatus status;

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

    public common.Role getRole() {
        return role;
    }

    public void setRole(common.Role role) {
        this.role = role;
    }

    public Timestamp getJoinAt() {
        return joinAt;
    }

    public void setJoinAt(Timestamp joinAt) {
        this.joinAt = joinAt;
    }

    public common.ParticipantStatus getStatus() {
        return status;
    }

    public void setStatus(common.ParticipantStatus status) {
        this.status = status;
    }

}
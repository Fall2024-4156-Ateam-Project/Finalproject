package dev.teamproject;

import java.sql.Timestamp;
import dev.teamproject.common;
import dev.teamproject.User;
import jakarta.persistence.*;
import java.io.Serializable;


@Entity
@Table(name = "Meeting")
public class Meeting implements Serializable{
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MID", nullable = false)
    private int mid;

    @ManyToOne
    @JoinColumn(name = "organizer_id", referencedColumnName = "UID")
    private User organizer;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private common.MeetingType type;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "start_time")
    private Timestamp startTime;

    @Column(name = "end_time")
    private Timestamp endTime;

    @Enumerated(EnumType.STRING)
    @Column(name = "recurrence")
    private common.Recurrence recurrence;

    @Column(name = "created_at")
    private Timestamp createdAt;

    @Column(name = "num_invite_participant")
    private Integer inviteParticipant;

    @Column(name = "num_accept_participant")
    private Integer acceptParticipant;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private common.MeetingStatus status;


    public int getMid() {
        return mid;
    }

//    public void setMid(int mid) {
//        this.mid = mid;
//    }

    public User getOrganizer() {
        return organizer;
    }

    public void setOrganizer(User organizer) {
        this.organizer = organizer;
    }

    public common.MeetingType getType() {
        return type;
    }

    public void setType(common.MeetingType type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Timestamp getStartTime() {
        return startTime;
    }

    public void setStartTime(Timestamp startTime) {
        this.startTime = startTime;
    }

    public Timestamp getEndTime() {
        return endTime;
    }

    public void setEndTime(Timestamp endTime) {
        this.endTime = endTime;
    }

    public common.Recurrence getRecurrence() {
        return recurrence;
    }

    public void setRecurrence(common.Recurrence recurrence) {
        this.recurrence = recurrence;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public Integer getInviteParticipant() {
        return inviteParticipant;
    }

    public void setInviteParticipant(Integer inviteParticipant) {
        this.inviteParticipant = inviteParticipant;
    }

    public Integer getAcceptParticipant() {
        return acceptParticipant;
    }

    public void setAcceptParticipant(Integer acceptParticipant) {
        this.acceptParticipant = acceptParticipant;
    }

    public common.MeetingStatus getStatus() {
        return status;
    }

    public void setStatus(common.MeetingStatus status) {
        this.status = status;
    }
}
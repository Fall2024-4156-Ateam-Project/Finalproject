package dev.teamproject.meeting;
import java.sql.Timestamp;
import dev.teamproject.common.commonTypes;
import dev.teamproject.user.User;
import jakarta.persistence.*;


@Entity
@Table(name = "Meeting")
public class Meeting {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MID", nullable = false)
    private int mid;

    @ManyToOne
    @JoinColumn(name = "organizer_id", referencedColumnName = "UID")
    private User organizer;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private commonTypes.MeetingType type;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "start_time")
    private Timestamp startTime;

    @Column(name = "end_time")
    private Timestamp endTime;

    @Enumerated(EnumType.STRING)
    @Column(name = "recurrence")
    private commonTypes.Recurrence recurrence;

    @Column(name = "created_at")
    private Timestamp createdAt;

    @Column(name = "num_invite_participant")
    private Integer inviteParticipant;

    @Column(name = "num_accept_participant")
    private Integer acceptParticipant;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private commonTypes.MeetingStatus status;


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

    public commonTypes.MeetingType getType() {
        return type;
    }

    public void setType(commonTypes.MeetingType type) {
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

    public commonTypes.Recurrence getRecurrence() {
        return recurrence;
    }

    public void setRecurrence(commonTypes.Recurrence recurrence) {
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

    public commonTypes.MeetingStatus getStatus() {
        return status;
    }

    public void setStatus(commonTypes.MeetingStatus status) {
        this.status = status;
    }
}
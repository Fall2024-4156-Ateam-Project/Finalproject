package dev.teamproject.timeslot;

import dev.teamproject.common.commonTypes;
import dev.teamproject.user.User;
import jakarta.persistence.*;

import java.time.LocalTime;
import java.util.Objects;

@Entity
@Table(name = "TimeSlot")
public class TimeSlot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TID")
    private int tid;

    @ManyToOne
    @JoinColumn(name = "UID", nullable = false)
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(name = "day", nullable = false)
    private commonTypes.Day day;

    @Column(name = "start_time", nullable = false)
    private LocalTime startTime;

    @Column(name = "end_time", nullable = false)
    private LocalTime endTime;

    @Enumerated(EnumType.STRING)
    @Column(name = "availability", nullable = false)
    private commonTypes.Availability availability;

    // Constructors
    public TimeSlot() {
    }

    public TimeSlot(User user, commonTypes.Day day, LocalTime startTime, LocalTime endTime, commonTypes.Availability availability) {
        this.user = user;
        this.day = day;
        this.startTime = startTime;
        this.endTime = endTime;
        this.availability = availability;
    }

    // Getters and Setters
    public int getTid() {
        return tid;
    }

    public void setTid(int tid) {
        this.tid = tid;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public commonTypes.Day getDay() {
        return day;
    }

    public void setDay(commonTypes.Day day) {
        this.day = day;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    public commonTypes.Availability getAvailability() {
        return availability;
    }

    public void setAvailability(commonTypes.Availability availability) {
        this.availability = availability;
    }

    // toString
    @Override
    public String toString() {
        return "TimeSlot{" +
                "tid=" + tid +
                ", user=" + user.getUid() +
                ", day=" + day +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", availability=" + availability +
                '}';
    }

    // equals and hashCode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TimeSlot timeSlot = (TimeSlot) o;

        return tid == timeSlot.tid;
    }

    @Override
    public int hashCode() {
        return Objects.hash(tid);
    }
}

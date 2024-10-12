package dev.teamproject.timeslot;

import dev.teamproject.common.commonTypes;
import dev.teamproject.user.User;
import jakarta.persistence.*;

import java.sql.Time;

@Entity
@Table(name = "TimeSlot")
public class TimeSlot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TID")
    private int tid;

    @ManyToOne
    @JoinColumn(name = "UID")
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(name = "day")
    private commonTypes.Day day;

    @Column(name = "start_time")
    private Time startTime;

    @Column(name = "end_time")
    private Time endTime;

    @Enumerated(EnumType.STRING)
    @Column(name = "availability")
    private commonTypes.Availability availability;


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

    public Time getStartTime() {
        return startTime;
    }

    public void setStartTime(Time startTime) {
        this.startTime = startTime;
    }

    public Time getEndTime() {
        return endTime;
    }

    public void setEndTime(Time endTime) {
        this.endTime = endTime;
    }

    public commonTypes.Availability getAvailability() {
        return availability;
    }

    public void setAvailability(commonTypes.Availability availability) {
        this.availability = availability;
    }

}
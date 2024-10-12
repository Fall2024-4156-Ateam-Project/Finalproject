package dev.teamproject.timeslot;

import dev.teamproject.common;
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
    private common.Day day;

    @Column(name = "start_time")
    private Time startTime;

    @Column(name = "end_time")
    private Time endTime;

    @Enumerated(EnumType.STRING)
    @Column(name = "availability")
    private common.Availability availability;


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

    public common.Day getDay() {
        return day;
    }

    public void setDay(common.Day day) {
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

    public common.Availability getAvailability() {
        return availability;
    }

    public void setAvailability(common.Availability availability) {
        this.availability = availability;
    }

}
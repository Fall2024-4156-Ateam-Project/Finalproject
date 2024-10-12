package dev.teamproject;

public class common {


    public enum MeetingType {
        group,
        one_on_one
    }

    public enum Recurrence {
        daily,
        weekly,
        monthly,
        none
    }


    public enum MeetingStatus {
        Valid,
        Invalid
    }

    public enum Role {
        participant,
        organizer
    }
    public enum ParticipantStatus {
        decline,
        accept,
        waiting
    }

    public enum Day {
        Monday, Tuesday, Wednesday, Thursday, Friday, Saturday, Sunday
    }

    public enum Availability {
        available, busy
    }

}

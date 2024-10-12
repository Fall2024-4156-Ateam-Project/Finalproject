package dev.teamproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import java.sql.Timestamp;
import java.sql.Time;
import java.util.List;
import java.util.ArrayList;

@SpringBootApplication
public class ProjectApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(ProjectApplication.class, args);
        System.out.println("Application has started!");
    }

    /**
     * This contains all the setup logic, it will mainly be focused
     * on loading up and creating an instance of the database based
     * off a saved file or will create a fresh database if the file
     * is not present.
     *
     * @param args A {@code String[]} of any potential runtime args
     */
    public void run(String[] args) {
        for (String arg : args) {
            if ("setup".equals(arg)) {
                myFileDatabase = new MyFileDatabase(1, "./data1.txt");
                testDataFile();
                System.out.println("System Setup");
                return;
            }
        }
        myFileDatabase = new MyFileDatabase(0, "./data1.txt");
        System.out.println("Start up");
    }

    //random case, use hard code for now, should use db later
    public void testDataFile() {
        // Create User
        User organizer = new User();
        organizer.setName("ABC");
        organizer.setEmail("ABC@email.com");

        // Create a Meeting instance
        Meeting meeting = new Meeting();
        meeting.setOrganizer(organizer);
        meeting.setType(common.MeetingType.group); // Meeting type
        meeting.setDescription("Monthly team sync to discuss project progress and next steps.");
        meeting.setStartTime(Timestamp.valueOf("2024-10-20 10:00:00")); // Start time
        meeting.setEndTime(Timestamp.valueOf("2024-10-20 11:00:00"));   // End time
        meeting.setRecurrence(common.Recurrence.none); // Recurrence
        meeting.setCreatedAt(new Timestamp(System.currentTimeMillis())); // Creation time
        meeting.setInviteParticipant(10); // Number of invited participants
        meeting.setAcceptParticipant(5);   // Number of accepted participants
        meeting.setStatus(common.MeetingStatus.Valid); // Status

        // Create a list of participants
        List<Participant> participants = new ArrayList<>();

        // Add participants to the meeting
        for (int i = 1; i <= 5; i++) {
            // Create a Participant instance
            Participant participant = new Participant();
            participant.setMeeting(meeting);
            participant.setUser(organizer);
            participant.setRole(common.Role.participant); // Set role
            participant.setJoinAt(new Timestamp(System.currentTimeMillis())); // Join time
            participant.setStatus(common.ParticipantStatus.accept); // Status of the participant

            // Add the participant to the list
            participants.add(participant);
        }

        // Create time slots for availability
        List<TimeSlot> timeSlots = new ArrayList<>();

        // Add time slots for the organizer
        TimeSlot timeSlot = new TimeSlot();
        timeSlot.setUser(organizer);
        timeSlot.setDay(common.Day.Monday); // Day of the week
        timeSlot.setStartTime(Time.valueOf("09:00:00")); // Start time
        timeSlot.setEndTime(Time.valueOf("17:00:00"));   // End time
        timeSlot.setAvailability(common.Availability.available); // Availability

        timeSlots.add(timeSlot); // Add time slot to the list

        // List of objects to save
        List<Object> objectsToSave = new ArrayList<>();
        objectsToSave.add(organizer);
        objectsToSave.add(meeting);
        objectsToSave.addAll(participants);
        objectsToSave.add(timeSlot);


        // saveContentsToFile should be on onTermination(), like individualproject
        myFileDatabase.setObjects(objectsToSave);
        myFileDatabase.saveContentsToFile();
    }


    public static MyFileDatabase myFileDatabase;
    private static boolean saveData = true;
}

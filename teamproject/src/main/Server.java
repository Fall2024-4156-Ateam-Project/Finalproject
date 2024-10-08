//package

import java.io.Serial;
import java.io.Serializable;

public class Server implements Serializable {
    @Serial
    private static long appointmentID = 123456L;
    private String name;
    private String Timeslot;
    private String Location;
    private int Capacity;

    public Server(String name, String Timeslot, String Location, int Capacity) {
        this.name = name;
        this.Timeslot = Timeslot;
        this.Location = Location;
        this.Capacity = Capacity;
    }

    public String getName() {
        return name;
    }
    public String getTimeslot() {
        return Timeslot;
    }

    public String getLocation() {
        return Location;
    }

    public int getCapacity() {
        return Capacity;
    }

    public void changeName(String newName){
        this.name = newName;
    }

    public void changeTimeslot(String newTimeslot){
        this.Timeslot = newTimeslot;
    }

    public void changeLocation(String newLocation){
        this.Location = newLocation;
    }

    public void changeCapacity(int newCapacity){
        this.Capacity = newCapacity;
    }
    public String toString(){
        return "\nname: " + name + " created an appointment at" + Timeslot +
                " with location: " + Location + " capacity: " + Capacity;
    }


}

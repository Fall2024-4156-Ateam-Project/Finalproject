//package

import java.io.Serial;
import java.io.Serializable;

/**
 * Create a appointment given holder, location and time
 * Taker should be none at first, but assign later
 * ID should be there, but random one
 */
public class Appointment implements Serializable{
    @Serial
    private static final long appointmentID = 123456L;
    private String Location;
    private String Server;
    private String TimeSlot;
    private String User;

/*
    Constructor
 */
public Appointment(String Location, String Server, String TimeSlot, String User) {
    this.Location = Location;
    this.Server = Server;
    this.TimeSlot = TimeSlot;
    this.User = User;
    }
/*
next four are getter
 */
public String getLocation() {
    return Location;
}

public String getServer() {
    return Server;
}

public String getTimeSlot() {
    return TimeSlot;
}

public String getUser() {
    return User;
}
    /*
    next four are setter
     */
public void changeLocation(String newlocation){
    this.Location = newlocation;
}

public void changeServer(String newserver){
    this.Server = newserver;
}

public void changeTimeSlot(String newtime){
    this.TimeSlot = newtime;
}

public void changeUser(String newuser){
    this.User = newuser;
}

public String toString(){
    return "\nServer: " + this.Server +  "; Location: "
            + this.Location +  "; Time: " + this.TimeSlot
            + "; User: " + this.User;
}


}
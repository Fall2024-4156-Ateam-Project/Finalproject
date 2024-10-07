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
    private String appointmentLocation;
    private String appointmentHolder;
    private String appointmentTimeSlot;
    private String appointmentTaker;

/*
    Constructor
 */
public Course(String appointmentLocation, String appointmentholder, String appointmentTimeSlot, String appointmentTaker) {
    this.appointmentLocation = appointmentLocation;
    this.appointmentHolder = appointmentHolder;
    this.appointmentTimeSlot = appointmentTimeSlot;
    this.appointmentTaker = appointmentTaker;
    }
/*
next four are getter
 */
public String getAppointmentLocation() {
    return appointmentLocation;
}

public String getAppointmentHolder() {
    return appointmentHolder;
}

public String getAppointmentTimeSlot() {
    return appointmentTimeSlot;
}

public String getAppointmentTaker() {
    return appointmentTaker;
}
    /*
    next four are setter
     */
public void changeLocation(String newlocation){
    this.appointmentLocation = newlocation;
}

public void changeholder(String newholder){
    this.appointmentholder = newholder;
}

public void changeTimeSlot(String newtime){
    this.appointmentTimeSlot = newtime;
}

public void changeTaker(String newtaker){
    this.appointmentTaker = newtaker;
}

public String toString(){
    return "\nHolder: " + this.appointmentHolder +  "; Location: "
            + this.appointmentLocation +  "; Time: " + this.appointmentTimeSlot
            + "; Taker: " + this.appointmentTaker;
}


}
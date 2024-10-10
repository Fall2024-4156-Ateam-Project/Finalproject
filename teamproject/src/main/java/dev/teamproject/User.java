package dev.teamproject;

import java.io.Serial;
import java.io.Serializable;

public class User implements Serializable {
    @Serial
    private static long appointmentID = 123456L;
    private String name;


public User(String name) {
    this.name = name;
}

public String getName() {
        return name;
    }

public void changeName(String newName){
        this.name = newName;
    }
public String toString(){
        return "\nname: " + name;
    }


}

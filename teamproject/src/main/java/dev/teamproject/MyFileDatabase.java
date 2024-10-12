package dev.teamproject;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * This class represents a file-based database that stores a list of objects.
 */
public class MyFileDatabase {

    /** The path to the file containing the database entries. */
    private String filePath;

    /** The list of objects to be stored in the database. */
    private List<Object> objectsList;

    /**
     * Constructs a MyFileDatabase object and initializes the objects list.
     *
     * @param flag     used to distinguish the mode of database
     * @param filePath the path to the file containing the entries of the database
     */
    public MyFileDatabase(int flag, String filePath) {
        this.filePath = filePath;
        this.objectsList = new ArrayList<>(); // Initialize the list
        if (flag == 0) {
            this.objectsList = deSerializeObjectFromFile(); // Load existing data if applicable
        }
    }

    /**
     * Sets the list of objects in the database.
     *
     * @param objects the list of objects to be stored
     */
    public void setObjects(List<Object> objects) {
        this.objectsList = objects;
    }

    /**
     * Gets the list of objects in the database.
     *
     * @return the list of objects
     */
    public List<Object> getObjects() {
        return this.objectsList;
    }

    /**
     * Deserializes the objects from the file and returns the list of objects.
     *
     * @return the deserialized list of objects
     */
    public List<Object> deSerializeObjectFromFile() {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(filePath))) {
            Object obj = in.readObject();
            if (obj instanceof List) {
                return (List<Object>) obj; // Cast to List<Object>
            } else {
                throw new IllegalArgumentException("Invalid object type in file.");
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return new ArrayList<>(); // Return empty list on error
        }
    }

    /**
     * Saves the contents of the objects list to the file. Contents of the file are
     * overwritten with this operation.
     */
    public void saveContentsToFile() {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filePath))) {
            out.writeObject(objectsList);
            System.out.println("Objects serialized successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Returns a string representation of the objects in the database.
     *
     * @return a string representation of the objects
     */
    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        for (Object obj : objectsList) {
            result.append(obj.toString()).append("\n"); // Customize as needed
        }
        return result.toString();
    }
}

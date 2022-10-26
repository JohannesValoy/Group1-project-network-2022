package no.ntnu.IDATA2304.Group1.clientApp.app2.logic;

import no.ntnu.IDATA2304.Group1.clientApp.app2.logic.Sensor;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a room in a house with an arraylist with one or more temperature sensors.
 * The room has a room number and a list of sensors.
 * The room can be updated with a new sensor.
 */
public class Room {


    private String name;
    private int roomNumber;
    public ArrayList<Sensor> sensorList = new ArrayList<>();

    /**
     * creates a room with a name and a roomnumber
     * @param name name of the room
     * @param roomNumber number of the room
     */
    public Room(int roomNumber, String name){
        sensorList.add(new Sensor("Temperature", roomNumber));
        this.name = name;
        this.roomNumber = roomNumber;
    }

    /**
     * Returns the name of the room.
     * @return String name.
     */
    public String getName(){
        return this.name;
    }

    /**
     * Returns the room number.
     * @return int roomNumber.
     */
    public int getRoomNumber(){
        return this.roomNumber;
    }

    /**
     * Adds a sensor to the room.
     * @param s the sensor to be added.
     */
    public void addSensor (Sensor s) {
        sensorList.add(s);
    }

    /**
     * Removes a sensor from the room.
     * @param s the sensor to be removed from the room.
     */
    public void removeSensor (Sensor s) {
        sensorList.add(s);
    }

    /**
     * Returns a list of all sensors in the room.
     * @return list of sensors.
     */
    public List<Sensor> getListOfSensors() {
        return sensorList;
    }

}

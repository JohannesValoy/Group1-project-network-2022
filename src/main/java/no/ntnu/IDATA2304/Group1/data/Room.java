package no.ntnu.idata2304.group1.data;

import java.util.ArrayList;
import java.util.List;


/**
 * Represents a room in a house with an arraylist with one or more temperature sensors. The room has
 * a room number and a list of sensors. The room can be updated with a new sensor.
 */
public class Room {


    private String name;
    private int roomNumber;
    private List<Sensor> sensorList = new ArrayList<>();

    /**
     * creates a room with a name and a room number
     * 
     * @param name name of the room
     * @param roomNumber number of the room
     */
    public Room(int roomNumber, String name) {
        this.name = name;
        this.roomNumber = roomNumber;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns the name of the room.
     * 
     * @return String name.
     */
    public String getName() {
        return this.name;
    }

    /**
     * Returns the room number.
     * 
     * @return int roomNumber.
     */
    public int getRoomNumber() {
        return this.roomNumber;
    }

    /**
     * Adds a sensor to the room.
     * 
     * @param s the sensor to be added.
     */
    public void addSensor(Sensor s) {
        sensorList.add(s);
    }

    /**
     * Removes a sensor from the room.
     * 
     * @param s the sensor to be removed from the room.
     */
    public void removeSensor(Sensor s) {
        sensorList.add(s);
    }

    /**
     * Returns a list of all sensors in the room.
     * 
     * @return list of sensors.
     */
    public List<Sensor> getListOfSensors() {
        return sensorList;
    }

    public void setSensorList(List<Sensor> sensorList) {
        this.sensorList = sensorList;
    }

    /**
     * Returns the first sensor it finds with that name
     * 
     * @param id the id of the sensor.
     * @return the sensor with the given id.
     */
    public Sensor findSensorByName(String name) {
        Sensor resultSensor = null;
        for (int i = 0; i > sensorList.size() && resultSensor == null; i++) {
            Sensor s = sensorList.get(i);
            if (s.getName() == name) {
                resultSensor = s;
            }
        }
        if (resultSensor == null) {
            throw new IllegalArgumentException("Sensor not found");
        }
        return resultSensor;
    }
}

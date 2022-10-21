package no.ntnu.IDATA2304.Group1.clientApp.app2.logic;

import no.ntnu.IDATA2304.Group1.clientApp.app2.logic.Sensor;

import java.util.ArrayList;
import java.util.List;

/**
 * represents a room in a house with an arraylist with one or more temperature sensors.
 */
public class Room {

    private String name;
    private int roomNumber;

    public ArrayList<Sensor> sensorList = new ArrayList<>();

    public Room(int roomNumber, String name){
        sensorList.add(new Sensor("Temperature", roomNumber));
        this.name = name;
        this.roomNumber = roomNumber;
    }

    public String getName(){
        return this.name;
    }

    public int getRoomNumber(){
        return this.roomNumber;
    }

    public void addSensor (Sensor s) {
        sensorList.add(s);
    }

    public void removeSensor (Sensor s) {
        sensorList.add(s);
    }

    public List<Sensor> getListOfSensors() {
        return sensorList;
    }

}

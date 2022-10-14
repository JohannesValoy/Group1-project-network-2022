package no.ntnu.IDATA2304.Group1.clientApp.app2.logic;

import java.util.ArrayList;
import java.util.List;

/**
 * represents a room in a house with an arraylist with one or more temperature sensors.
 */
public class Room {

    public static ArrayList<Sensor> sensorList = new ArrayList<>();

    public void addSensor (Sensor s) {
        sensorList.add(s);
    }

    public void removeSensor (Sensor s) {
        sensorList.add(s);
    }

    public List getListOfSensors() {
        return sensorList;
    }

    public void printListByType(int type) {
            for (Sensor search : sensorList) {
                if(search.getName().contains(type)) {
                    System.out.println(search.toString());
                }
            }
    }

}

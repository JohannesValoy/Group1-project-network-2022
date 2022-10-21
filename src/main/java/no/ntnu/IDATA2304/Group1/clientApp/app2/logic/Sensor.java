package no.ntnu.IDATA2304.Group1.clientApp.app2.logic;


import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * Represents a rain sensor measuring rain per day measured in millimeters.
 */
public class Sensor {
    private List<Integer> historyLog;
    private Integer currentLevel = 0; // Current rain level in mm
    private Random randomGen;
    private String type;
    private int id;

    public Sensor(String type, int id) {
        this.historyLog = new LinkedList<>();
        this.randomGen = new Random();
    }

    public String getName(){
        return this.type;
    }

    public int getCurrentLevel() {
        this.currentLevel = this.currentLevel + (3 - randomGen.nextInt(6));
        historyLog.add(this.currentLevel);
        return this.currentLevel;
    }

    public List<Integer> getHistoryLog() {
        return this.historyLog;
    }


}
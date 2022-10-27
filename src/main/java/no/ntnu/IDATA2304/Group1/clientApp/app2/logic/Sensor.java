package no.ntnu.idata2304.group1.clientApp.app2.logic;


import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * Represents a sensor measuring a certain value specified by the type.
 */
public class Sensor {
    private List<Integer> historyLog;
    private Integer currentLevel = 0; // Current reading
    private Random randomGen;
    private String type;
    private int id;

    public Sensor(String type, int id) {
        this.historyLog = new LinkedList<>();
        this.randomGen = new Random();
        this.type = type;
        this.id = id;
        for(int i = 0; i < 1000; i++){
            getCurrentLevel();
        }
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

    public int getId() {
        return this.id;
    }

    public String getType() {
        return this.type;
    }

}
package no.ntnu.idata2304.group1.data;


import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * Represents a sensor doing measurement. Sensor type is decided by string type.
 */
public class Sensor {
    public enum Types {
        TEMPERATURE("Temperature"), HUMIDITY("Humidity");

        private String name;

        Types(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }

    private List<RoomRecord> historyLog; // Log of all readings from the sensor
    private Integer currentLevel = 0; // Current reading
    private Random randomGen; // Random generator making example data
    private final Types type; // Type of sensor
    private int id; // ID of sensor

    public Sensor(Types type, int id) {
        this.historyLog = new LinkedList<>();
        this.randomGen = new Random();
        this.type = type;
        this.id = id;
        // Loads ExampleValues
        for (int i = 0; i < 10; i++) {
            updateReading();
        }
    }

    /**
     * Returns the name of the sensor.
     * 
     * @return string Name.
     */
    public String getTypeName() {
        return this.type.getName();
    }

    /**
     * Updates sensor readings
     */
    public void updateReading() {
        this.currentLevel = (this.currentLevel + (500 - randomGen.nextInt(1000)) / 200);
        historyLog.add(new RoomRecord(new Date(), this.currentLevel));
        System.out.println(historyLog.size());
    }

    /**
     * Returns the history log of the sensor.
     * 
     * @return List historyLog.
     */
    public List<RoomRecord> getHistoryLog() {
        updateReading();
        return this.historyLog;
    }

    /**
     * Returns the ID of the sensor.
     * 
     * @return int id.
     */
    public int getId() {
        return this.id;
    }

    /**
     * Returns the type of the sensor.
     * 
     * @return String type.
     */
    public Types getType() {
        return this.type;
    }

}

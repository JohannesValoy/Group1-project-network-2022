package no.ntnu.idata2304.group1.data;


import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * Represents a sensor doing measurement. Sensor type is decided by string type.
 */
public class Sensor {
    /**
     * The Different types of sensors.
     */
    public enum Types {
        TEMPERATURE("Temperature"), HUMIDITY("Humidity");

        private String name;

        /**
         * Creates a sensor type.
         * 
         * @param name name of the sensor type.
         */
        Types(String name) {
            this.name = name;
        }

        /**
         * Returns the name of the sensor type.
         * 
         * @return String name.
         */
        public String getName() {
            return name;
        }

        /**
         * Returns the enum type from a string.
         * 
         * @param name the name of the type.
         * @return the enum type.
         * @throws IllegalArgumentException if the name is not a valid type or if it can't be found.
         */
        public static Types getTypeByName(String name) throws IllegalArgumentException {
            if (name == null || name.isEmpty()) {
                throw new IllegalArgumentException("Name can't be null or empty");
            }
            Types result = null;
            for (int i = 0; i < Types.values().length && result == null; i++) {
                Types type = Types.values()[i];
                if (type.getName().equalsIgnoreCase(name)) {
                    result = type;
                }
            }
            if (result == null) {
                throw new IllegalArgumentException("No type with name " + name + " found");
            }
            return result;
        }
    }

    private List<SensorRecord> historyLog; // Log of all readings from the sensor
    private Integer currentLevel = 0; // Current reading
    private Random randomGen; // Random generator making example data
    private final Types type; // Type of sensor
    private String name; // ID of sensor

    public Sensor(Types type, String name) {
        this.historyLog = new LinkedList<>();
        this.randomGen = new Random();
        this.type = type;
        this.name = name;
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
        historyLog.add(new SensorRecord(new Date(), this.currentLevel));
        System.out.println(historyLog.size());
    }

    /**
     * Returns the history log of the sensor.
     * 
     * @return List historyLog.
     */
    public List<SensorRecord> getHistoryLog() {
        updateReading();
        return this.historyLog;
    }

    /**
     * Returns the name of the sensor.
     * 
     * @return String the name.
     */
    public String getName() {
        return this.name;
    }

    /**
     * Returns the type of the sensor.
     * 
     * @return String type.
     */
    public Types getType() {
        return this.type;
    }

    public boolean addRecord(SensorRecord roomRecord) {
        return this.historyLog.add(roomRecord);
    }
}

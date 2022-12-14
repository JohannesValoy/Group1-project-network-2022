package no.ntnu.idata2304.group1.data;


import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

/**
 * Represents a sensor doing measurement. Sensor type is decided by string type.
 */
public class Sensor implements Serializable {
    /**
     * The Different types of sensors.
     */
    public enum Types {
        /**
         * Temperature types.
         */
        TEMPERATURE("Temperature"),
        /**
         * Humidity types.
         */
        HUMIDITY("Humidity"),
        /**
         * Undefined types.
         */
        UNDEFINED("Undefined");

        private final String name;

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
            Types result = UNDEFINED;
            for (int i = 0; i < Types.values().length && result == UNDEFINED; i++) {
                Types type = Types.values()[i];
                if (type.getName().equalsIgnoreCase(name)) {
                    result = type;
                }
            }
            return result;
        }
    }

    private final List<SensorRecord> historyLog; // Log of all readings from the sensor
    private final Types type; // Type of sensor
    private final String name; // ID of sensor

    /**
     * Uses type as type of sensor and name as the name of the sensor
     *
     * @param type as Temperature or humidity
     * @param name as the name/ID of the sensor
     */
    public Sensor(Types type, String name) {
        this.historyLog = new LinkedList<>();
        this.type = type;
        this.name = name;
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
     * Returns the history log of the sensor.
     *
     * @return List historyLog.
     */
    public List<SensorRecord> getHistoryLog() {
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

    /**
     * Add record boolean.
     *
     * @param roomRecord the room record
     */
    public void addRecord(SensorRecord roomRecord) {
        this.historyLog.add(roomRecord);
    }
}
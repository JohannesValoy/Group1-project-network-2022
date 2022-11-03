package no.ntnu.idata2304.group1.data;

import java.sql.Timestamp;
import java.util.Date;

/**
 * A class for storing room data
 */
public class SensorRecord {
    final Date date;
    final double value;

    /**
     * Creates a new RoomRecord
     * 
     * @param date The date of the record
     * @param temperature The temperature of the room
     */
    public SensorRecord(Date date, double value) {
        this.date = date;
        this.value = value;
    }


    /**
     * Gets the date of the record
     * 
     * @return The date of the record
     */
    public Date getDate() {
        return date;
    }

    /**
     * Gets the temperature of the room
     * 
     * @return The temperature of the room
     */
    public double getValue() {
        return value;
    }
}

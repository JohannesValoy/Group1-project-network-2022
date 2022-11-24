package no.ntnu.idata2304.group1.data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * A class for storing sensor data
 */
public record SensorRecord(LocalDateTime date, double value) implements Serializable {

}

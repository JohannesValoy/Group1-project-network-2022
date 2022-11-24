package no.ntnu.idata2304.group1.data;

import java.io.Serializable;
import java.util.Date;

/**
 * A class for storing sensor data
 */
public record SensorRecord(Date date, double value) implements Serializable{
  
}

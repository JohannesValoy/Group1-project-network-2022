package no.ntnu.IDATA2304.Group1.clientApp.app2.logic;

/**
 * Receives data from sensor using https request.
 */
public class Sensor {
    private int humidity;
    private int temperature;

    public getName(int type) {
        if(type == 1) {
            System.out.println(humidity);
        }
        if (type == 2) {
            System.out.println(temperature);
        }

    }
}

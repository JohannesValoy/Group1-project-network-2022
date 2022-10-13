package no.ntnu.IDATA2304.Group1.clientApp.weatherstation;

import java.util.Random;

public class WindSensor {
    private int currentWindSpeed; //in m/s
    private int currentWindDirection; // In degrees 0 - 360

    private Random randomGenerator;

    public WindSensor() {
        this.currentWindSpeed = 0;
        this.currentWindDirection = 0;
        this.randomGenerator = new Random();
    }

    public int getWindSpeed() {
        // Return a random speed between 0 and 19 m/s
        return this.randomGenerator.nextInt(20);
    }

    public int getWindDirection() {
        // Return a random wind direction between 0 and 359 degrees
        return this.randomGenerator.nextInt(360);
    }

}

package no.ntnu.idata2304.group1.sensor;

import no.ntnu.idata2304.group1.sensor.network.NodeSocket;
import no.ntnu.idata2304.group1.sensor.sensors.Sensor;

/**
 * Represents the whole application, including the logic.
 */
public class SensorApplication {
    private static final long SLEEP_DURATION_MS = 2000;
    double lastTemperatureReading;
    double lastHumidityReading;
    NodeSocket nodeSocket;
    Sensor temperatureSensor;
    Sensor humiditySensor;
    private static String serverAdress = "localhost";
    private static int serverPort = 6008;

    /**
     * Creates a new sensor application.
     */
    public SensorApplication() {
        nodeSocket = new NodeSocket(serverAdress, serverPort);
        temperatureSensor = new Sensor(Sensor.Types.TEMPERATURE);
        humiditySensor = new Sensor(Sensor.Types.HUMIDITY);
    }

    /**
     * Run the application, does not return, except if something goes wrong.
     *
     * @throws IllegalStateException If something went wrong during the process
     */


    public void run() throws IllegalStateException {
        initializeSensors();
        while (true) {
            readAllSensors();
            sendDataToServer();
            powerNap();
        }
    }

    /**
     * Initializes all the sensors
     *
     * @throws IllegalStateException If some sensors are not found
     */
    private void initializeSensors() throws IllegalStateException {
        SensorProvider sensorProvider = SensorProvider.getInstance();
        temperatureSensor = sensorProvider.getTemperatureSensor();
        if (temperatureSensor == null) {
            throw new IllegalStateException("Temperature sensor not found");
        }
        humiditySensor = sensorProvider.getHumiditySensor();
        if (humiditySensor == null) {
            throw new IllegalStateException("Humidity sensor not found");
        }
        nodeSocket = new NodeSocket()
    }

    private void readAllSensors() {
        System.out.println("Reading sensor data...");
        lastTemperatureReading = temperatureSensor.readValue();
        lastHumidityReading = humiditySensor.readValue();
    }

    private void sendDataToServer() {

        System.out.println("Sending data to server:");
        System.out.println("  temp: " + lastTemperatureReading + "C");
        System.out.println("");
    }

    private void powerNap() {
        try {
            Thread.sleep(SLEEP_DURATION_MS);
        } catch (InterruptedException e) {
            System.out.println("Ooops, someone woke us up in the middle of a nap");
        }
    }
}

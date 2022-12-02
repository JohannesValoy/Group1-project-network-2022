package no.ntnu.idata2304.group1.sensor;

import java.io.IOException;
import java.util.logging.Logger;
import no.ntnu.idata2304.group1.data.Sensor;
import no.ntnu.idata2304.group1.sensor.network.NodeSocket;
import no.ntnu.idata2304.group1.sensor.sensors.BoundedSensor;
import no.ntnu.idata2304.group1.sensor.sensors.RoomHumiditySensor;
import no.ntnu.idata2304.group1.sensor.sensors.RoomTemperatureSensor;

/**
 * Represents the whole application, including the logic.
 */
public class SensorApplication implements Runnable {
    private long SLEEP_DURATION_MS = 2000;
    private double lastTemperatureReading;
    private NodeSocket nodeSocket;
    private BoundedSensor sensor;
    private String apiKey;
    private static final Logger logger = Logger.getLogger(SensorApplication.class.getName());

    /**
     * Creates a new sensor application.
     *
     * @param name the name
     * @param apiKey the api key
     * @param serverAdresse the server adresse
     * @param serverPort the server port
     * @param customCerts the custom certs
     * @throws IOException the io exception
     */
    public SensorApplication(String name, String apiKey, String serverAdresse, int serverPort,
            String customCerts) throws IOException {
        nodeSocket = new NodeSocket(serverAdresse, serverPort, customCerts);
        sensor = new RoomTemperatureSensor(name);
        this.apiKey = apiKey;
    }

    public SensorApplication(String name, String apiKey, String serverAdresse, int serverPort,
            String customCerts, Sensor.Types type) throws IOException {
        nodeSocket = new NodeSocket(serverAdresse, serverPort, customCerts);
        if (type == Sensor.Types.TEMPERATURE) {
            sensor = new RoomTemperatureSensor(name);
        } else if (type == Sensor.Types.HUMIDITY) {
            sensor = new RoomHumiditySensor(name);
        } else {
            throw new IllegalArgumentException("Unknown sensor type");
        }
        this.apiKey = apiKey;
    }


    /**
     * Run the application, does not return, except if something goes wrong.
     *
     * @throws IllegalStateException If something went wrong during the process
     */


    public void run() throws IllegalStateException {
        readAllSensors();
        sendDataToServer();
        powerNap();
    }


    private void readAllSensors() {
        logger.info("Reading sensor data...");
        lastTemperatureReading = sensor.readValue();
    }

    private void sendDataToServer() {
        nodeSocket.sendData(lastTemperatureReading, apiKey);
    }

    private void powerNap() {
        try {
            Thread.sleep(SLEEP_DURATION_MS);
        } catch (InterruptedException e) {
            logger.warning("Ooops, someone woke us up in the middle of a nap");
        }
    }

    public void setNapTime(long nappis) throws IllegalArgumentException {
        if (nappis < 500) {
            throw new IllegalArgumentException("NapTime can not be less than 500ms");
        }
        this.SLEEP_DURATION_MS = nappis;
    }
}

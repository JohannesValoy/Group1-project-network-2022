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
     * @param name          the name
     * @param apiKey        the api key
     * @param serverAddress the server address
     * @param serverPort    the server port
     * @param customCerts   the custom certs
     * @throws IOException the io exception
     */
    public SensorApplication(String name, String apiKey, String serverAddress, int serverPort,
            String customCerts) throws IOException {
        nodeSocket = new NodeSocket(serverAddress, serverPort, customCerts);
        sensor = new RoomTemperatureSensor(name);
        this.apiKey = apiKey;
    }

    /**
     * Instantiates a new Sensor application.
     *
     * @param name          the name
     * @param apiKey        the api key
     * @param serverAddress the server address
     * @param serverPort    the server port
     * @param customCerts   the custom certs
     * @param type          the type
     * @throws IOException the io exception
     */
    public SensorApplication(String name, String apiKey, String serverAddress, int serverPort,
            String customCerts, Sensor.Types type) throws IOException {
        nodeSocket = new NodeSocket(serverAddress, serverPort, customCerts);
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
            logger.warning("Failed to wake up");
        }
    }

    /**
     * Sets nap time.
     *
     * @param nap the nappis
     * @throws IllegalArgumentException the illegal argument exception
     */
    public void setNapTime(long nap) throws IllegalArgumentException {
        if (nap < 500) {
            throw new IllegalArgumentException("NapTime can not be less than 500ms");
        }
        this.SLEEP_DURATION_MS = nap;
    }
}

package no.ntnu.idata2304.group1.sensor;

import java.util.logging.Logger;
import no.ntnu.idata2304.group1.data.Sensor.Types;

/**
 * The type Main.
 */
public class Main {

    private static int port = 6008;
    private static String certPath = null;
    private static final Logger LOGGER = Logger.getLogger(Main.class.getSimpleName());

    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
    public static void main(String[] args) {
        if (args.length < 4) {
            LOGGER.warning(
                    "Usage: java -jar sensor.jar <sensor type (Currently supporting Temperature, Humidity)> <sensor name> <api key> <server address> <optional server port> <optional trustedCertificate folder> ");
            System.exit(1);
        }
        String sensorType = args[0];
        String sensorId = args[1];
        String apiKey = args[2];
        String serverAdresse = args[3];
        if (args.length > 4) {
            port = Integer.parseInt(args[4]);
        }
        if (args.length > 5) {
            certPath = args[5];
        }
        SensorApplication sensor = null;
        try {
            sensor = new SensorApplication(sensorId, apiKey, serverAdresse, port, certPath,
                    Types.getTypeByName(sensorType));
        } catch (Exception e) {
            LOGGER.severe(e.getMessage());
            System.exit(1);
        }

        sensor.run();


    }
}

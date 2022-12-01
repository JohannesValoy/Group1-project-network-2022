package no.ntnu.idata2304.group1.sensor;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.junit.jupiter.api.Test;


/**
 * A hacky solution for running multiple sensors at the same time.
 */
public class TestSensors {
    // SERVERDetails
    private static String SERVER_ADDRESS = "10.24.90.163";
    private static int SERVER_PORT = 6008;

    // Folder for the test certs
    private static String CUSTOMCERTS =
            ".\\src\\test\\resources\\no\\ntnu\\idata2304\\group1\\clientapp\\app2\\network\\trustedCerts";



    // Only simulating 4 sensors. The keys are within the testData that is injected into the test
    // database.
    private static String[] keys = {"abcdefhijklm", "123456789000", "dskfsafsjfs7", "ioffsagapfap"};
    private static String[] names = {"Sensor 1", "Sensor 2", "Sensor 3", "Sensor 4", "Sensor 5",
            "Sensor 6", "Sensor 7", "Sensor 8", "Sensor 9", "Sensor 10"};


    /**
     * Starts a number of sensors equal to the number of keys and names.
     */
    @Test
    public void RunMultipleSensors() {
        ExecutorService pool = Executors.newFixedThreadPool(Math.min(keys.length, names.length));
        ArrayList<SensorApplication> sensors = new ArrayList<>();
        System.out.println("Starting the application...");
        for (int i = 0; i < keys.length && i < names.length; i++) {
            try {
                SensorApplication sensor = new SensorApplication(names[i], keys[i], SERVER_ADDRESS,
                        SERVER_PORT, CUSTOMCERTS);
                sensors.add(sensor);

            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }

        }
        while (true) {
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
            }
            for (SensorApplication s : sensors) {
                pool.execute(s);
            }
        }
    }
}

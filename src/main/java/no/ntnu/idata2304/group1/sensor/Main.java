package no.ntnu.idata2304.group1.sensor;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


/**
 * A hacky solution for running multiple sensors at the same time.
 */
public class Main {

    private static String SERVER_ADDRESS = "localhost";
    private static int SERVER_PORT = 6008;
    private static String CUSTOMCERTS = null;

    private static ArrayList<SensorApplication> sensors = new ArrayList<>();


    // Only simulating 10 sensors. The keys are within the testData that is injected into the test
    // database. Should be in another file, but this is just a hacky solution.
    private static String[] keys =
            {"abc-def-hij-klm", "123-456-789-000", "dsk-fsa-fsj-fs7", "iof-fsa-gap-fap"};
    private static String[] names = {"Sensor 1", "Sensor 2", "Sensor 3", "Sensor 4"};
    private static ExecutorService pool =
            Executors.newFixedThreadPool(Math.min(keys.length, names.length));

    /**
     * Start the application
     * 
     * @param args Not used
     */
    public static void main(String[] args) {
        if (args.length == 3) {
            SERVER_ADDRESS = args[0];
            SERVER_PORT = Integer.parseInt(args[1]);
            CUSTOMCERTS = args[2];
        }
        System.out.println("Starting the application...");
        for (int i = 0; i < keys.length && i < names.length; i++) {
            try {
                SensorApplication sensor = new SensorApplication(keys[i], names[i], SERVER_ADDRESS,
                        SERVER_PORT, CUSTOMCERTS);
                sensors.add(sensor);
                pool.execute(sensor);
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
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
}

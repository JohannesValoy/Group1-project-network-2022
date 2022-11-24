package no.ntnu.idata2304.group1.sensor;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


/**
 * A hacky solution for running multiple sensors at the same time.
 */
public class Main {

    private static String SERVER_ADDRESS = "10.24.90.163";
    private static int SERVER_PORT = 6008;
    private static String CUSTOMCERTS = "C:\\Users\\johan\\Desktop\\Group1testfolder\\src\\test\\resources\\no\\ntnu\\idata2304\\group1\\clientapp\\app2\\network\\trustedCerts";

    private static ArrayList<SensorApplication> sensors = new ArrayList<>();


    // Only simulating 10 sensors. The keys are within the testData that is injected into the test
    // database. Should be in another file, but this is just a hacky solution.
    private static String[] keys =
            {"abcdefhijklm", "123456789000", "dskfsafsjfs7", "ioffsagapfap"};
    private static String[] names = {"Sensor 1", "Sensor 2", "Sensor 3", "Sensor 4", "Sensor 5",
            "Sensor 6", "Sensor 7", "Sensor 8", "Sensor 9", "Sensor 10"};
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

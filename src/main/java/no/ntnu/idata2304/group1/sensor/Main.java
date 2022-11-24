package no.ntnu.idata2304.group1.sensor;

/**
 * Main entrypoint for the application
 */
public class Main {

    private static final String SERVER_ADDRESS = "localhost";
    private static final int SERVER_PORT = 6008;
    private static final String CUSTOMCERTS = null;


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
        App app = new App();
        try {
            App.run();
        } catch (Exception e) {
            System.out.println("Oops: " + e.getMessage());
        }
        System.out.println("Application finished");
    }
}

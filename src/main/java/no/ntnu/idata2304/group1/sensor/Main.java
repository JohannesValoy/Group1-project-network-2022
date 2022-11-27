package no.ntnu.idata2304.group1.sensor;

public class Main {
    public static void main(String[] args) {
        if (args.length < 4) {
            System.out.println(
                    "Usage: java -jar sensor.jar <sensor type (Temperature, Humidity)> <sensor name> <api key> <server address>  <optional server port> <optional trustedCertifcate folder> ");
            System.exit(1);
        }
        String sensorType = args[0];
        String sensorId = args[1];

    }
}

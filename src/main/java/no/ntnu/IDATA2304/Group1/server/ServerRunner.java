package no.ntnu.idata2304.group1.server;

import java.io.IOException;
import java.sql.SQLException;
import no.ntnu.idata2304.group1.server.database.DBConnector;
import no.ntnu.idata2304.group1.server.network.TCPListener;

public class ServerRunner {

    static int port = 6008;

    public static void main(String[] args) throws IOException, SQLException {
        // if (args.length != 1){
        // throw new IllegalArgumentException("Need one argument for the port number");
        // }
        // int port = Integer.parseInt(args[1]);
        DBConnector database = new DBConnector();
        try (TCPListener listener = new TCPListener(port)) {
            listener.run();
        }
        database.close();
    }
}

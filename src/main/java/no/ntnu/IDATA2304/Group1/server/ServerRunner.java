package no.ntnu.idata2304.group1.server;

import java.io.IOException;
import java.sql.SQLException;

import no.ntnu.idata2304.group1.server.database.DBConnector;
import no.ntnu.idata2304.group1.server.messages.LogOutputer;
import no.ntnu.idata2304.group1.server.network.TCPListener;

public class ServerRunner {

    static int port = 6008;

    public static void main(String[] args) throws IOException, SQLException {
        LogOutputer.print(LogOutputer.MessageType.INFO, "Starting the server");
        int port = Integer.parseInt(args[1]);
        DBConnector database = new DBConnector();
        LogOutputer.print(LogOutputer.MessageType.INFO, "Connected to the database to the database");
        try (TCPListener listener = new TCPListener(port)) {
            listener.run();
        }
        database.close();
    }
}

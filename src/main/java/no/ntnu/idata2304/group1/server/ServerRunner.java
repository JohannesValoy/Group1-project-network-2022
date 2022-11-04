package no.ntnu.idata2304.group1.server;

import java.io.IOException;

import no.ntnu.idata2304.group1.server.database.DBConnector;
import no.ntnu.idata2304.group1.server.messages.LogOutputer;
import no.ntnu.idata2304.group1.server.messages.LogOutputer.MessageType;
import no.ntnu.idata2304.group1.server.network.TCPListener;

/**
 * The main class for the server
 */
public class ServerRunner {

    static int port = 6008;

    /**
     * Starts the server
     * 
     * @param args The arguments to the program
     * @throws IOException if the server fails to start å
     */
    public static void main(String[] args) throws IOException {
        LogOutputer.print(MessageType.INFO, "Starting the server");
        DBConnector database = new DBConnector();
        LogOutputer.print(LogOutputer.MessageType.INFO,
                "Connected to the database to the database");
        try (TCPListener listener = new TCPListener(port)) {
            listener.run();
        }
        database.close();
    }
}

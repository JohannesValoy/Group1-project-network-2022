package no.ntnu.idata2304.group1.server;

import java.io.IOException;
import java.sql.SQLException;
import no.ntnu.idata2304.group1.server.database.DBConnector;
import no.ntnu.idata2304.group1.server.messages.LogOutputer;
import no.ntnu.idata2304.group1.server.messages.LogOutputer.MessageType;
import no.ntnu.idata2304.group1.server.network.SeverSSLKeyFactory;
import no.ntnu.idata2304.group1.server.network.listener.JavaListener;
import no.ntnu.idata2304.group1.server.network.listener.TCPListener;

/**
 * The main class for the server
 */
public class ServerRunner {

    /**
     * Starts the server
     * 
     * @param args The arguments to the program the following arguments are supported:
     *        <ul>
     *        First argument: The keyStore path
     *        </ul>
     *        <ul>
     *        Second argument: The keystore password
     *        </ul>
     * @throws IOException if the server fails to start å
     */
    public static void main(String[] args) throws IOException, SQLException {
        LogOutputer.print(MessageType.INFO, "Starting the server");
        if (args.length < 2) {
            LogOutputer.print(MessageType.ERROR, "Too few arguments");
            return;
        }
        if (!SeverSSLKeyFactory.testKeyStore(args[0], args[1])) {
            LogOutputer.print(MessageType.ERROR, "Could not find the keystore");
            throw new IOException("Could not find the keystore");
        }

        DBConnector database = new DBConnector(true);
        LogOutputer.print(LogOutputer.MessageType.INFO,
                "Connected to the database to the database");
        try (TCPListener listener = new JavaListener(args[0], args[1])) {
            listener.run();
        }
        database.close();
    }
}

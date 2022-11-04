package no.ntnu.idata2304.group1.server.network;

import java.io.Closeable;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import no.ntnu.idata2304.group1.server.messages.LogOutputer;
import no.ntnu.idata2304.group1.server.messages.LogOutputer.MessageType;

/**
 * Responsible for listening for new connections and creating new threads for each connection
 */
// TODO: Support for SSL
// TODO: Support connection trough HTTP
public class TCPListener extends Thread implements Closeable {
    private ServerSocket socket;

    /**
     * Creates a new TCP listener
     * 
     * @param port The port to listen on
     * @throws IOException if the socket fails to connect
     */
    public TCPListener(int port) throws IOException {
        this.socket = new ServerSocket(port);
    }

    @Override
    public void run() {
        while (true) {
            LogOutputer.print(MessageType.INFO, "Starting to listening for clients");
            try {
                Socket client = socket.accept();
                new ClientThread(client).start();
            } catch (IOException e) {
                LogOutputer.print(MessageType.ERROR, "Error connecting to a client");
            }
        }
    }

    @Override
    public void close() throws IOException {
        socket.close();
    }
}

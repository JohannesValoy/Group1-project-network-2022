package no.ntnu.idata2304.group1.server.network;

import java.io.Closeable;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import no.ntnu.idata2304.group1.server.messages.LogOutputer;
import no.ntnu.idata2304.group1.server.messages.LogOutputer.MessageType;

public class TCPListener extends Thread implements Closeable {
    private ServerSocket socket;

    public TCPListener(int port) throws IOException {
        this.socket = new ServerSocket(port);
    }

    @Override
    public void run() {
        while (true) {
            LogOutputer.print(MessageType.INFO, "Starting to listening for clients");
            try {
                Socket client = socket.accept();
                new ClientThread(client).run();
            } catch (IOException e) {
                LogOutputer.print(MessageType.ERROR, "Error connecting to a client");
            } ;
        }
    }

    @Override
    public void close() throws IOException {
        socket.close();
    }
}

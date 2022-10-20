package no.ntnu.idata2304.group1.server.network;

import java.io.Closeable;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import javax.net.ssl.SSLServerSocketFactory;

public class TCPListener extends Thread implements Closeable {
    private ServerSocket socket;
    private int port;
    private ArrayList<Socket> clients;

    public TCPListener(int port) throws IOException {
        this.port = port;
        this.socket = SSLServerSocketFactory.getDefault().createServerSocket(port);
        this.clients = new ArrayList<>();
    }

    public void run() {
        while (true) {
            try {
                Socket client = socket.accept();
                new ClientThread(client).start();
            } catch (Exception e) {
            } ;
        }
    }

    @Override
    public void close() throws IOException {
        socket.close();
    }
}

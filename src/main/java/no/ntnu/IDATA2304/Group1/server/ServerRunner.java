package no.ntnu.idata2304.group1.server;
import java.io.IOException;

import no.ntnu.idata2304.group1.server.communicator.TCPListener;

public class ServerRunner {

    static int port = 1234;

    public static void main(String[] args) throws IOException {
        if (args.length != 1){
            throw new IllegalArgumentException("Need one argument for the port number");
        } 
        //int port = Integer.parseInt(args[1]);
        try (TCPListener listener = new TCPListener(port)) {
            listener.run();
        }
    }
}


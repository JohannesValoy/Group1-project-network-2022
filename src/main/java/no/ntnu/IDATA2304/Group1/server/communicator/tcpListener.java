package no.ntnu.IDATA2304.Group1.server.communicator;
import java.io.IOException;
import java.net.ServerSocket;

class tcpListener{
    private ServerSocket socket;
    private int port;

    public tcpListener(int port) throws IOException{
        this.port = port;
        this.socket = new ServerSocket(port);
    }
    
    public void run(){
        
    }
}
package no.ntnu.idata2304.group1.server.network;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import no.ntnu.idata2304.group1.data.network.Message;

/**
 * Responsible for sending and receiving network packages
 * 
 * @author Mathias J. Kirkeby
 */

public class JavaClient extends ClientThread {
    private ObjectOutputStream output;
    private ObjectInputStream input;

    /**
     * Creates a new client thread
     * 
     * @param socket The socket to use
     * @throws IOException if the socket fails to connect
     */
    public JavaClient(Socket socket) throws IOException {
        super(socket);
        this.input = new ObjectInputStream(socket.getInputStream());
        this.output = new ObjectOutputStream(socket.getOutputStream());
    }

    /**
     * Sends a response to the client
     * 
     * @param response The response to send
     * @throws IOException if the response fails to send
     */
    public void sendResponse(Message response) throws IOException {
        boolean notSent = true;
        for (int i = 0; i < 3 && notSent; i++) {
            try {
                output.writeObject(response);
                notSent = false;
            } catch (Exception e) {
                if (i <= 3) {
                    throw new IOException("Tried 3 times and still failed");
                }
            }
        }
    }

    protected Message getRequest() throws IOException {
        Message request;
        try {
            request = (Message) input.readObject();
        } catch (ClassNotFoundException e) {
            throw new IllegalArgumentException("This is not a valid request");
        }
        return request;
    }
}

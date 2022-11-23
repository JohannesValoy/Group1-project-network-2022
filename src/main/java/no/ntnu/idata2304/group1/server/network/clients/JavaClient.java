package no.ntnu.idata2304.group1.server.network.clients;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import javax.net.ssl.SSLSocket;
import no.ntnu.idata2304.group1.data.network.Message;

/**
 * Responsible for sending and receiving network packages with Java serialization
 * 
 * @author Mathias J. Kirkeby
 */

public class JavaClient extends ClientRunnable {
    private InputStream detectStream;
    private ObjectOutputStream output;
    private ObjectInputStream input;

    /**
     * Creates a new JavaClient
     * 
     * @param socket The socket to use
     * @throws IOException if the socket fails to connect
     */
    public JavaClient(SSLSocket socket) throws IOException {
        super(socket);
        this. detectStream = socket.getInputStream();
        this.input = new ObjectInputStream(detectStream);
        this.output = new ObjectOutputStream(socket.getOutputStream());
    }

    @Override
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

    @Override
    protected Message getRequest() throws IOException {
        Message request = null;
            try {
                request = (Message) input.readObject();
            } catch (ClassNotFoundException e) {
                throw new IllegalArgumentException("This is not a valid request");
            }
        return request;
    }
}

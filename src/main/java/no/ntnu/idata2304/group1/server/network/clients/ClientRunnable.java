package no.ntnu.idata2304.group1.server.network.clients;

import java.io.IOException;
import java.util.logging.Logger;
import javax.net.ssl.SSLSocket;
import no.ntnu.idata2304.group1.data.network.Message;
import no.ntnu.idata2304.group1.data.network.responses.ErrorMessage;
import no.ntnu.idata2304.group1.server.network.handlers.RequestHandler;

/**
 * Responsible for sending and receiving network packages
 *
 * @author Mathias J. Kirkeby
 */
public abstract class ClientRunnable implements Runnable {
    private final SSLSocket socket;

    private static final Logger logger = Logger.getLogger(ClientRunnable.class.getName());

    /**
     * Instantiates a new Client runnable.
     *
     * @param socket the socket
     * @throws IOException the io exception
     */
    protected ClientRunnable(SSLSocket socket) {
        this.socket = socket;
    }

    /**
     * This function starts the client thread.
     */
    @Override
    public void run() {
        if (socket.isConnected()) {
            try {
                Message request = getRequest();
                if (request != null) {
                    logger.info(
                            "Received request from " + socket.getInetAddress().getHostAddress());
                    Message response = RequestHandler.getResponse(request);
                    sendResponse(response);
                }
            } catch (IllegalArgumentException e) {
                try {
                    sendResponse(new ErrorMessage(e.getMessage()));
                } catch (Exception f) {
                    logger.severe(f.getMessage());
                }
            } catch (IOException e) {
                try {
                    socket.close();
                } catch (IOException f) {
                    logger.severe("Tried closing the socket. Got error: " + f.getMessage());
                }
            }
        }
    }

    /**
     * A function that sends the data to the client
     *
     * @param response the response
     * @throws IllegalArgumentException the illegal argument exception
     * @throws IOException              the io exception
     */
    public abstract void sendResponse(Message response)
            throws IllegalArgumentException, IOException;

    /**
     * Tries to recieve a message from the client. If no message is recieved, it
     * will return null
     *
     * @return request
     * @throws IllegalArgumentException the illegal argument exception
     * @throws IOException              the io exception
     */
    protected abstract Message getRequest() throws IllegalArgumentException, IOException;

    /**
     * Checks if the client is closed
     *
     * @return True if the client is closed
     */
    public boolean isClosed() {
        return socket.isClosed();
    }

}

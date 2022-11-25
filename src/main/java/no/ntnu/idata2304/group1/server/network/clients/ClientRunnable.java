package no.ntnu.idata2304.group1.server.network.clients;

import java.io.EOFException;
import java.io.IOException;
import java.util.logging.Logger;
import javax.net.ssl.SSLSocket;
import no.ntnu.idata2304.group1.data.network.Message;
import no.ntnu.idata2304.group1.data.network.responses.ErrorMessage;
import no.ntnu.idata2304.group1.server.messages.LogOutputer;
import no.ntnu.idata2304.group1.server.messages.LogOutputer.MessageType;
import no.ntnu.idata2304.group1.server.network.handlers.RequestHandler;

/**
 * Responsible for sending and receiving network packages
 *
 * @author Mathias J. Kirkeby
 */
public abstract class ClientRunnable implements Runnable {
    private SSLSocket socket;
    private RequestHandler handler;
    private boolean running;

    private static final Logger logger = Logger.getLogger(ClientRunnable.class.getName());

    /**
     * Instantiates a new Client runnable.
     *
     * @param socket the socket
     * @throws IOException the io exception
     */
    protected ClientRunnable(SSLSocket socket) throws IOException {
        this.socket = socket;
        this.handler = new RequestHandler();
        this.running = false;
    }

    /**
     * This function starts the client thread.
     */
    @Override
    public void run() {
        running = true;
        if (socket.isConnected()) {
            try {
                Message request = getRequest();
                if (request != null) {
                    logger.info(
                            "Received request from " + socket.getInetAddress().getHostAddress());
                    Message response = handler.getResponse(request);
                    sendResponse(response);
                }
            } catch (EOFException e) {

            } catch (IllegalArgumentException e) {
                try {
                    sendResponse(new ErrorMessage(e.getMessage()));
                } catch (Exception f) {
                    LogOutputer.print(MessageType.ERROR, f.getMessage());
                }
            } catch (IOException e) {

            }

        }
        running = false;
    }


    /**
     * A function that sends the data to the client
     *
     * @param response the response
     * @throws IllegalArgumentException the illegal argument exception
     * @throws IOException the io exception
     */
    public abstract void sendResponse(Message response)
            throws IllegalArgumentException, IOException;

    /**
     * Tries to recieve a message from the client. If no message is recieved, it will return null
     *
     * @return request
     * @throws IllegalArgumentException the illegal argument exception
     * @throws IOException the io exception
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

    /**
     * Checks if the client is running
     *
     * @return True if the client is running
     */
    public boolean isRunning() {
        return running;
    }

    /**
     * Set as running.
     */
    public synchronized void setAsRunning() {
        running = true;
    }
}

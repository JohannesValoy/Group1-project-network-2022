package no.ntnu.idata2304.group1.server.network.clients;

import java.io.EOFException;
import java.io.IOException;
import javax.net.ssl.SSLSocket;
import no.ntnu.idata2304.group1.data.network.Message;
import no.ntnu.idata2304.group1.data.network.responses.ErrorMessage;
import no.ntnu.idata2304.group1.server.messages.LogOutputer;
import no.ntnu.idata2304.group1.server.messages.LogOutputer.MessageType;
import no.ntnu.idata2304.group1.server.requests.RequestHandler;

/**
 * Responsible for sending and receiving network packages
 * 
 * @author Mathias J. Kirkeby
 */

public abstract class ClientRunnable implements Runnable {
    private SSLSocket socket;
    private RequestHandler handler;
    private boolean running;

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
                LogOutputer.print(MessageType.INFO,
                        "The request was: " + request.getType().toString());
                Message response = handler.getResponse(request);
                LogOutputer.print(MessageType.INFO, "Replying with: " + response);
                sendResponse(response);
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
     */
    protected abstract void sendResponse(Message response)
            throws IllegalArgumentException, IOException;

    /**
     * Tries to recieve the data given
     * 
     * @return
     * @throws IllegalArgumentException
     * @throws IOException
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
}

package no.ntnu.idata2304.group1.server.network;

import java.io.EOFException;
import java.io.IOException;
import java.net.Socket;
import no.ntnu.idata2304.group1.data.network.Message;
import no.ntnu.idata2304.group1.data.network.responses.ErrorMessage;
import no.ntnu.idata2304.group1.server.messages.LogOutputer;
import no.ntnu.idata2304.group1.server.messages.LogOutputer.MessageType;
import no.ntnu.idata2304.group1.server.requests.RequestHandler;

public abstract class ClientThread extends Thread {
    private Socket socket;
    private RequestHandler handler;

    protected ClientThread(Socket socket) throws IOException {
        this.socket = socket;
        this.handler = new RequestHandler();
    }

    /**
     * This function starts the client thread.
     */
    @Override
    public void run() {
        while (socket.isConnected()) {
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

    }


    /**
     * A function that sends the data to the client
     */
    public abstract void sendResponse(Message response)
            throws IllegalArgumentException, IOException;

    /**
     * Tries to recieve the data given
     * 
     * @return
     * @throws IllegalArgumentException
     * @throws IOException
     */
    protected abstract Message getRequest() throws IllegalArgumentException, IOException;

}

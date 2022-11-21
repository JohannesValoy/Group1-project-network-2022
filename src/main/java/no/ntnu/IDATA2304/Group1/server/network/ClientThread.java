package no.ntnu.idata2304.group1.server.network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import no.ntnu.idata2304.group1.server.requests.RequestHandler;
import no.ntnu.idata2304.group1.data.network.Message;
import no.ntnu.idata2304.group1.server.messages.LogOutputer;
import no.ntnu.idata2304.group1.server.messages.LogOutputer.MessageType;

/**
 * Responsible for sending and receiving network packages
 * 
 * @author Mathias J. Kirkeby
 */

public class ClientThread extends Thread {
    private Socket socket;
    private RequestHandler handler;
    private BufferedReader reader;
    private ObjectOutputStream output;
    private ObjectInputStream input;

    /**
     * Creates a new client thread
     * 
     * @param socket The socket to use
     * @throws IOException if the socket fails to connect
     */
    public ClientThread(Socket socket) throws IOException {
        this.socket = socket;
        this.handler = new RequestHandler();
        this.output = new ObjectOutputStream(socket.getOutputStream());
        this.input = new ObjectInputStream(socket.getInputStream());
        this.reader = new BufferedReader(new InputStreamReader(input));
    }


    @Override
    public void run() {
        while (socket.isConnected()) {
            try {
                while (reader.ready() && input.available() > 0) {
                    // String request = reader.readLine()
                    Message request = (Message) input.readObject();
                    LogOutputer.print(MessageType.INFO,
                            "The request was: " + request.getType().toString());
                    Message response = handler.getResponse(request);
                    LogOutputer.print(MessageType.INFO, "Replying with: " + response);
                    sendResponse(response);
                }
            } catch (IOException ex) {
                try {
                    socket.close();
                } catch (Exception e) {
                } ;
            } catch (NumberFormatException ne) {

            } catch (ClassNotFoundException e) {

            } catch (Exception e) {
                // I tried
            }
        }

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
}

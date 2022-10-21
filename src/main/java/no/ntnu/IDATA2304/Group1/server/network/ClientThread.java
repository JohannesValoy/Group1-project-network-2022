package no.ntnu.idata2304.group1.server.network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

import java.net.Socket;
import java.util.Iterator;
import java.util.stream.Stream;
import no.ntnu.idata2304.group1.server.requests.RequestHandler;
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
    private OutputStream output;

    public ClientThread(Socket socket) throws IOException {
        this.socket = socket;
        this.handler = new RequestHandler();
        this.output = socket.getOutputStream();
        this.reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }

    // TODO: Implement Login
    public void run() {
        try {
            while (this.socket.isConnected() && reader.ready()) {
                String request = reader.readLine();
                if (!request.isEmpty()) {
                    StringBuilder messageBuilder = new StringBuilder(request);
                    Iterator<String> requestIterator = reader.lines().iterator();
                    while (requestIterator.hasNext()) {
                        messageBuilder.append(requestIterator.next());
                    }
                    request = messageBuilder.toString();
                    LogOutputer.print(MessageType.INFO, "The request was: " + request);
                    String response = handler.getResponse(request);
                    LogOutputer.print(MessageType.INFO, "Replying with: " + response);
                    output.write(response.getBytes());
                }
            }
        } catch (IOException ex) {
            System.out.println("Server exception: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
}

package no.ntnu.idata2304.group1.server.network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

import java.net.Socket;
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
    private InputStream input;

    public ClientThread(Socket socket) throws IOException {
        this.socket = socket;
        this.handler = new RequestHandler();
        this.output = socket.getOutputStream();
        this.input = socket.getInputStream();
        this.reader = new BufferedReader(new InputStreamReader(input));
    }

    // TODO: Implement Login
    public void run() {
        while (socket.isConnected()) {
            try {
                while (reader.ready() && input.available() > 0) {
                    String request = reader.readLine();
                    char[] buffer = new char[Integer.parseInt(request)];
                    reader.read(buffer);
                    request = buffer.toString();
                    LogOutputer.print(MessageType.INFO, "The request was: " + request);
                    String response = handler.getResponse(request);
                    LogOutputer.print(MessageType.INFO, "Replying with: " + response);
                    response = response.length() + "\n" + response;
                    SendResponse(response);
                }
            } catch (IOException ex) {
                try {
                    socket.close();
                } catch (Exception e) {
                } ;
            } catch (NumberFormatException ne) {
                try {
                    input.skip(input.available());
                    SendResponse(
                            "{ \"code\":\"error\", \"message\" : \"The first line must consist of only a number that says how long the message is.\"}");
                } catch (Exception e) {
                    // I tried
                }
            }
        }

    }

    public void SendResponse(String response) throws IOException {
        response = response.length() + "\n" + response;
        boolean notSent = true;
        for (int i = 0; i < 3 && notSent; i++) {
            try {
                output.write(response.getBytes());
                notSent = false;
            } catch (Exception e) {
                if (!(i > 3)) {
                    throw new IOException("Tried 3 times and still failed");
                }
            }
        }

    }
}

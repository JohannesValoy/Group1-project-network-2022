package no.ntnu.idata2304.group1.server.network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;

import org.json.JSONObject;
import no.ntnu.idata2304.group1.server.requests.access.User;

/**
 * This thread is responsible to handle client connection.
 *
 * @author www.codejava.net
 */

public class ClientThread extends Thread{
    private Socket socket;
    private User access;

    public ClientThread(Socket socket) {
        this.socket = socket;
    }

    public void run() {
        try {
            InputStream input = socket.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(input));

            OutputStream output = socket.getOutputStream();

            while (this.socket.isConnected() && reader.ready()) {
                String firstline = reader.readLine();
                try {
                    int length = Integer.parseInt(firstline);
                    char[] buffer = new char[length];
                    reader.read();
                    System.out.println("Received: " + new String(buffer));
                    JSONObject messageJSON = new JSONObject(new String(buffer));
                } catch (Exception e) {
                    if (firstline.equals("null")) {
                        socket.close();
                    }
                    else{
                        String message = "{'response': 'Error','Reason':'The request was invalid'}";
                        int length = message.length();
                        output.write((length+"\n"+message).getBytes());
                    }
                }
            }
        } catch (IOException ex) {
            System.out.println("Server exception: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
}

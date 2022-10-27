package no.ntnu.idata2304.group1.clientApp.app2;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Socket class on client side to connect and read from server
 */
public class ClientSocket {

    /**
     * Template
     */
    public static void main(String[] args) {

        String hostname = "localhost";
        int port = 6008;

        try (Socket socket = new Socket(hostname, port)) {

            InputStream input = socket.getInputStream();
            InputStreamReader reader = new InputStreamReader(input);

            int character;
            StringBuilder data = new StringBuilder();

            while ((character = reader.read()) != -1) {
                data.append((char) character);
            }

            System.out.println(data);

        } catch (UnknownHostException ex) {

            System.out.println("Server not found: " + ex.getMessage());

        } catch (IOException ex) {

            System.out.println("I/O error: " + ex.getMessage());
        }
    }
}


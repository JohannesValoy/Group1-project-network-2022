package no.ntnu.idata2304.group1.server.communicator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;

/**
 * This thread is responsible to handle client connection.
 *
 * @author www.codejava.net
 */

 public class ClientThread extends Thread {
    private Socket socket;
 
    public ClientThread(Socket socket) {
        this.socket = socket;
    }
 
    public void run() {
        try {
            InputStream input = socket.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(input));
 
            OutputStream output =  socket.getOutputStream();

            while(this.socket.isConnected() && reader.ready()){
                String firstline = reader.readLine();

                try{int length = Integer.parseInt(firstline);
                char[] buffer = new char[length];
                reader.read(buffer);
                System.out.println(buffer.toString());}
                catch(Exception e){
                  if(firstline.equals("null")) {
                    socket.close();
                  }
                }
                //JSONTokener JSONObject = new JSONTokener(buffer.toString());
            }

            socket.close();
        } catch (IOException ex) {
            System.out.println("Server exception: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
}

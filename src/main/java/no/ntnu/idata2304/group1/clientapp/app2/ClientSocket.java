package no.ntnu.idata2304.group1.clientapp.app2;

import no.ntnu.idata2304.group1.data.network.Message;
import no.ntnu.idata2304.group1.data.network.requests.GetMessage;
import no.ntnu.idata2304.group1.data.network.responses.ErrorMessage;
import no.ntnu.idata2304.group1.data.network.responses.ResponseRoomMessage;


import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * Socket class on client side to connect and read from server
 */
public class ClientSocket {

    Socket socket;
    ObjectInputStream input;
    ObjectOutputStream output;

    public ClientSocket(String hostname, int port) throws IOException {
        this.socket = new Socket(hostname, port);
        this.output = new ObjectOutputStream(socket.getOutputStream());
        this.input = new ObjectInputStream(socket.getInputStream());
    }


    public Message response() throws IOException, ClassNotFoundException {

           Message messageResponse = (Message) input.readObject();


        return switch (messageResponse.getType()) {
            case OK -> (ResponseRoomMessage) messageResponse;
            case ERROR -> (ErrorMessage) messageResponse;

            default -> throw new IllegalStateException(
                    "Unexpected value: " + messageResponse.getType());
        };
    }

    public void outputObject(ArrayList<String> rooms) throws IOException {
        output.writeObject(new GetMessage(GetMessage.Types.ROOM_TEMP, rooms));
    }
}


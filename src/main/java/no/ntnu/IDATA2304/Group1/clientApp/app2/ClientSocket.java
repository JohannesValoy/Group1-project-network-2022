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

    String hostname = "localhost";
    int port = 6008;
    Socket socket = new Socket(hostname, port);
    ObjectInputStream input;
    ObjectOutputStream output;

    public ClientSocket() throws IOException {
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

    public void outputObject(List<String> rooms) throws IOException {
        output.writeObject(new GetMessage(GetMessage.Types.ROOM_TEMP, (ArrayList<String>) rooms));
    }
}


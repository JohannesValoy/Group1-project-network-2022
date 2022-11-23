package no.ntnu.idata2304.group1.clientapp.app2.network;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.net.SocketFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import no.ntnu.idata2304.group1.data.Room;
import no.ntnu.idata2304.group1.data.network.Message;
import no.ntnu.idata2304.group1.data.network.requests.get.GetLogsMessage;
import no.ntnu.idata2304.group1.data.network.responses.ErrorMessage;
import no.ntnu.idata2304.group1.data.network.responses.OKMessage;

/**
 * Socket class on client side to connect and read from server
 * 
 */
public class ClientSocket {

    private SSLSocket socket;
    private ObjectInputStream input;
    private ObjectOutputStream output;

    public ClientSocket(String hostname, int port) throws IOException {
        SocketFactory factory = SSLSocketFactory.getDefault();
        this.socket = (SSLSocket) factory.createSocket(hostname, port);
        this.socket.startHandshake();
        this.output = new ObjectOutputStream(socket.getOutputStream());
        this.input = new ObjectInputStream(socket.getInputStream());
    }

    public ClientSocket(String hostname, int port, String folderForCustomCert) throws IOException {
        SSLContext context = SSLTrustFactory.createTrustStore(folderForCustomCert);
        if (context == null) {
            throw new IOException("Could not create SSL context");
        }

        SSLSocketFactory factory = context.getSocketFactory();
        this.socket = (SSLSocket) factory.createSocket(hostname, port);
        this.output = new ObjectOutputStream(socket.getOutputStream());
        this.input = new ObjectInputStream(socket.getInputStream());
    }

    public Message response() throws IOException, ClassNotFoundException {

        Message messageResponse = (Message) input.readObject();


        return switch (messageResponse.getType()) {
            case OK -> (OKMessage) messageResponse;
            case ERROR -> (ErrorMessage) messageResponse;

            default -> throw new IllegalStateException(
                    "Unexpected value: " + messageResponse.getType());
        };
    }

    public void getRoomData(List<String> rooms) throws IOException {
        output.writeObject(
                new GetLogsMessage(GetLogsMessage.Logs.TEMPERATURE, (ArrayList<String>) rooms));
    }

    public ArrayList<Room> getListOfRooms() {
        return null;
    public Map<String, Room> getRoomData(List<String> rooms) throws IOException, ClassNotFoundException {
        output.writeObject(new GetMessage(GetMessage.Types.ROOM_TEMP, (ArrayList<String>) rooms));
        Message messageResponse = response();
        Map<String, Room> data = new HashMap<String, Room>();
        if (response().getType() == Message.Types.OK) {
            ResponseRoomMessage response = (ResponseRoomMessage) response();
            data = (Map<String, Room>) response.getData();
        }
        return data;
    }

}


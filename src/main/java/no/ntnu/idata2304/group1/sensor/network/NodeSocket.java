package no.ntnu.idata2304.group1.sensor.network;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.net.SocketFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import no.ntnu.idata2304.group1.clientapp.app2.network.SSLTrustFactory;
import no.ntnu.idata2304.group1.data.Room;
import no.ntnu.idata2304.group1.data.network.Message;
import no.ntnu.idata2304.group1.data.network.requests.add.AddMessage;
import no.ntnu.idata2304.group1.data.network.requests.get.GetLogsMessage;
import no.ntnu.idata2304.group1.data.network.responses.DataMessage;
import no.ntnu.idata2304.group1.data.network.responses.ErrorMessage;
import no.ntnu.idata2304.group1.data.network.responses.OKMessage;

/**
 * Socket class on client side to connect and read from server
 */
public class NodeSocket {

    private SSLSocket socket;
    private ObjectInputStream input;
    private ObjectOutputStream output;

    /**
     * Instantiates a new Node socket.
     *
     * @param hostname the hostname
     * @param port     the port
     * @throws IOException the io exception
     */
    public NodeSocket(String hostname, int port) throws IOException {
        SocketFactory factory = SSLSocketFactory.getDefault();
        this.socket = (SSLSocket) factory.createSocket(hostname, port);
        this.socket.startHandshake();
        this.output = new ObjectOutputStream(socket.getOutputStream());
        this.input = new ObjectInputStream(socket.getInputStream());
    }

    /**
     * Instantiates a new Node socket.
     *
     * @param hostname            the hostname
     * @param port                the port
     * @param folderForCustomCert the folder for custom cert
     * @throws IOException the io exception
     */
    public NodeSocket(String hostname, int port, String folderForCustomCert) throws IOException {
        SSLContext context = SSLTrustFactory.createTrustStore(folderForCustomCert);
        if (context == null) {
            throw new IOException("Could not create SSL context");
        }

        SSLSocketFactory factory = context.getSocketFactory();
        this.socket = (SSLSocket) factory.createSocket(hostname, port);
        this.output = new ObjectOutputStream(socket.getOutputStream());
        this.input = new ObjectInputStream(socket.getInputStream());
    }

    /**
     * Response message.
     *
     * @return the message
     * @throws IOException            the io exception
     * @throws ClassNotFoundException the class not found exception
     */
    public Message response() throws IOException, ClassNotFoundException {

        Message messageResponse = (Message) input.readObject();


        return switch (messageResponse.getType()) {
            case OK -> (OKMessage) messageResponse;
            case ERROR -> (ErrorMessage) messageResponse;

            default -> throw new IllegalStateException(
                    "Unexpected value: " + messageResponse.getType());
        };
    }


    /**
     * Add room data array list.
     *
     * @param rooms the rooms
     * @return the array list
     * @throws IOException            the io exception
     * @throws ClassNotFoundException the class not found exception
     */
    public ArrayList<Room> addRoomData(List<String> rooms)
            throws IOException, ClassNotFoundException {
        output.writeObject(
                new GetLogsMessage(GetLogsMessage.Logs.TEMPERATURE, (ArrayList<String>) rooms));
        Message messageResponse = response();
        ArrayList<Room> data = new ArrayList<>();
        if (messageResponse.getType() == Message.Types.OK) {
            DataMessage response = (DataMessage) messageResponse;
            for (Iterator<Object> it = response.getData(); it.hasNext();) {
                Room room = (Room) it.next();
                data.add(room);
            }

        }
        return data;
    }

    /**
     * Send data.
     *
     * @param lastTemperatureReading the last temperature reading
     * @param apiKey                 the api key
     */
    public void sendData(double lastTemperatureReading, String apiKey) {
        try {
            output.writeObject(
                    new AddMessage(AddMessage.Command.LOG, apiKey, lastTemperatureReading));
        } catch (Exception e) {

        }
    }

}


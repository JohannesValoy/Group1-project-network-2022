package no.ntnu.idata2304.group1.clientapp.app2;

import java.util.ArrayList;
import org.junit.jupiter.api.Test;
import no.ntnu.idata2304.group1.data.network.Message;

public class TestClientSocket {

    @Test
    public void testClientSocket() throws Exception {
        ClientSocket clientSocket = new ClientSocket("localhost", 6008);
        ArrayList<String> rooms = new ArrayList<>();
        rooms.add("A");
        rooms.add("B");
        clientSocket.getRoomData(rooms);
        Message message = clientSocket.response();
        System.out.println("Message: " + message);
    }
}

package no.ntnu.idata2304.group1.clientapp.app2;

import no.ntnu.idata2304.group1.data.network.Message;
import no.ntnu.idata2304.group1.server.network.listener.JavaListener;
import no.ntnu.idata2304.group1.server.network.listener.TCPListener;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.fail;

class ClientSocketTest {

    @Test
    void testStuff() throws IOException {
        ArrayList<String> rooms = new ArrayList<>();
        rooms.add("C220");
        try (TCPListener server = new JavaListener(
                TCPListener.class.getResource("serverKeys").getPath().replace("%20", " "), "123")) {
            server.start();
            ClientSocket clientSocket = new ClientSocket("localhost", 6008, ClientSocketTest.class
                    .getResource("trustedCerts").getPath().replace("%20", " "));
            clientSocket.getRoomData(rooms);
            Message message = clientSocket.response();
            System.out.println("Message: " + message);
        } catch (ClassNotFoundException e) {
            fail("Class not found");
        }
    }
}

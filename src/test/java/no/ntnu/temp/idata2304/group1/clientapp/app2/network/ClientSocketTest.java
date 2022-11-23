package no.ntnu.temp.idata2304.group1.clientapp.app2.network;

import org.junit.jupiter.api.Test;
import no.ntnu.idata2304.group1.clientapp.app2.network.ClientSocket;
import no.ntnu.idata2304.group1.data.network.Message;
import no.ntnu.idata2304.group1.data.network.responses.ErrorMessage;
import no.ntnu.idata2304.group1.server.network.listener.JavaListener;
import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.fail;

class ClientSocketTest {

    @Test
    void testStuff() throws IOException {
        ArrayList<String> rooms = new ArrayList<>();
        rooms.add("C220");
        try (JavaListener server = new JavaListener(
                JavaListener.class.getResource("TestKeys").getPath().replace("%20", " "), "123")) {
            server.start();
            ClientSocket clientSocket = new ClientSocket("localhost", 6008, ClientSocketTest.class
                    .getResource("trustedCerts").getPath().replace("%20", " "));
            clientSocket.getRoomData(rooms);
            Message message = clientSocket.response();
            if (message.getType().equals(Message.Types.ERROR)) {
                ErrorMessage ermessage = (ErrorMessage) message;
                System.out.println("Error message: " + ermessage.getErrorMessage());
            } else {
                System.out.println("Message: " + message);
            }

        } catch (ClassNotFoundException e) {
            fail("Class not found");
        }
    }
}

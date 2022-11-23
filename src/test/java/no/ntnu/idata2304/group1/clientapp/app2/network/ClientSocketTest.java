package no.ntnu.idata2304.group1.clientapp.app2.network;

import org.junit.jupiter.api.Test;
import no.ntnu.idata2304.group1.data.network.Message;
import no.ntnu.idata2304.group1.data.network.responses.ErrorMessage;
import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.fail;

class ClientSocketTest {
    /**
     * Needs to have a active server running on localhost:6008 or change the address in the
     * ClientSocket constructor. Application is not happy about server and client running on the
     * same machine
     * @throws ClassNotFoundException
     */
    @Test
    void testStuff() throws IOException, ClassNotFoundException {
        ArrayList<String> rooms = new ArrayList<>();
        rooms.add("C220");

        ClientSocket clientSocket = new ClientSocket("localhost", 6008,
                ClientSocketTest.class.getResource("trustedCerts").getPath().replace("%20", " "));
        clientSocket.getRoomData(rooms);
        Message message = null;
        try {
            message = clientSocket.response();
        } catch (Exception e) {
            fail("Could not get response");
        }
        if (message.getType().equals(Message.Types.ERROR)) {
            ErrorMessage ermessage = (ErrorMessage) message;
            System.out.println("Error message: " + ermessage.getErrorMessage());
        } else {
            System.out.println("Message: " + message);
        }

    }
}

package no.ntnu.idata2304.group1.clientapp.app2;

import no.ntnu.idata2304.group1.server.network.TCPListener;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.function.Try;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class ClientSocketTest {

    @Test
    void testStuff() throws IOException {
        List<String> rooms = new ArrayList<>();
        rooms.add("C220");
        try(TCPListener server = new TCPListener(6008)){
            server.start();
            ClientSocket socket = new ClientSocket("10.24.90.163",6008);
            socket.outputObject(rooms);
            assertDoesNotThrow(socket::response);
        };
    }
}
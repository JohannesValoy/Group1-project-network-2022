package no.ntnu.idata2304.group1.server.network;

import org.junit.jupiter.api.Test;

public class TCPListenerTest {

    @Test
    public void checkThatItRuns() throws Exception {
        TCPListener listener = new TCPListener(6008, "server.jks", "");
    }
}

package no.ntnu.idata2304.group1.server.network;

import org.junit.jupiter.api.Test;

public class TCPListenerTest {

    @Test
    public void checkThatItRuns() throws Exception {
        if (TCPListener.class.getResourceAsStream("TestKeys") == null) {
            throw new Exception("Could not find the keystore");
        }
        TCPListener listener = new TCPListener(6008,
                TCPListener.class.getResource("TestKeys").getPath().toString().replace("%20", " "),
                "123");
    }
}


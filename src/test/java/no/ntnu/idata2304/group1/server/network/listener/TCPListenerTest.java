package no.ntnu.idata2304.group1.server.network.listener;

import org.junit.jupiter.api.Test;


public class TCPListenerTest {

    @Test
    public void checkThatItRuns() throws Exception {
        if (TCPListener.class.getResourceAsStream("TestKeys") == null) {
            throw new Exception("Could not find the keystore");
        }
        TCPListener listener = new JavaListener(6008,
                TCPListener.class.getResource("TestKeys").getPath().toString().replace("%20", " "),
                "123");
    }

    /**
     * This test is not really a test, it is just here to make sure that the server can be started
     * and receives a message
     *
     */
    @Test
    public void messageTest() {

        try (TCPListener listener = new JavaListener(6008,
                TCPListener.class.getResource("TestKeys").getPath().toString().replace("%20", " "),
                "123");) {
            listener.run();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


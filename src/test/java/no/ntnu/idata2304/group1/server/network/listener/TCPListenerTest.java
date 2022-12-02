package no.ntnu.idata2304.group1.server.network.listener;

import org.junit.jupiter.api.Test;

/**
 * The type Tcp listener test.
 */
public class TCPListenerTest {

    /**
     * Check that it runs.
     *
     * @throws Exception the exception
     */
    @Test
    public void checkThatItRuns() throws Exception {
        if (TCPListener.class.getResourceAsStream("test.p12") == null) {
            throw new Exception("Could not find the keystore");
        }
        TCPListener listener = new JavaListener(6008,
                TCPListener.class.getResource("test.p12").getPath().toString().replace("%20", " "),
                "1234");
    }

    /**
     * This test is not really a test, it is just here to make sure that the server
     * can be started
     * and receives a message
     */
    @Test
    public void messageTest() {

        try (JavaListener listener = new JavaListener(6008,
                TCPListener.class.getResource("test.p12").getPath().toString().replace("%20", " "),
                "1234");) {
            listener.run();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
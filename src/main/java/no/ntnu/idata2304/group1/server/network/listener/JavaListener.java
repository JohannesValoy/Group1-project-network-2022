package no.ntnu.idata2304.group1.server.network.listener;

import java.io.IOException;
import javax.net.ssl.SSLSocket;
import no.ntnu.idata2304.group1.server.network.clients.ClientRunnable;
import no.ntnu.idata2304.group1.server.network.clients.JavaClient;

/**
 * The type Java listener.
 */
public class JavaListener extends TCPListener {

    /**
     * Instantiates a new Java listener.
     *
     * @param port             the port
     * @param keyStoreName     the key store name
     * @param keyStorePassword the key store password
     * @throws IOException the io exception
     */
    public JavaListener(int port, String keyStoreName, String keyStorePassword) throws IOException {
        super(port, keyStoreName, keyStorePassword);
    }

    /**
     * Instantiates a new Java listener.
     *
     * @param keyStoreName     the key store name
     * @param keyStorePassword the key store password
     * @throws IOException the io exception
     */
    public JavaListener(String keyStoreName, String keyStorePassword) throws IOException {
        super(6008, keyStoreName, keyStorePassword);
    }

    @Override
    public ClientRunnable createClient(SSLSocket client) throws IOException {
        return new JavaClient(client);
    }

}

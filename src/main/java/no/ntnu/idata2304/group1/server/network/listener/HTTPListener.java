package no.ntnu.idata2304.group1.server.network.listener;

import java.io.IOException;
import javax.net.ssl.SSLSocket;
import no.ntnu.idata2304.group1.server.network.clients.ClientRunnable;
import no.ntnu.idata2304.group1.server.network.clients.HTTPClient;

public class HTTPListener extends TCPListener {

    protected HTTPListener(int port, String keyStoreName, String keyStorePassword)
            throws IOException {
        super(port, keyStoreName, keyStorePassword);
    }

    protected HTTPListener(String keyStoreName, String keyStorePassword) throws IOException {
        super(443, keyStoreName, keyStorePassword);
    }

    @Override
    public ClientRunnable createClient(SSLSocket client) throws IOException {
        return new HTTPClient(client);
    }

}

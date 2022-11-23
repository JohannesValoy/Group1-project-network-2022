package no.ntnu.idata2304.group1.server.network.listener;

import java.io.IOException;
import javax.net.ssl.SSLSocket;
import no.ntnu.idata2304.group1.server.network.clients.ClientRunnable;
import no.ntnu.idata2304.group1.server.network.clients.JavaClient;

public class JavaListener extends TCPListener {

    public JavaListener(int port, String keyStoreName, String keyStorePassword) throws IOException {
        super(port, keyStoreName, keyStorePassword);
    }

    public JavaListener(String keyStoreName, String keyStorePassword) throws IOException {
        super(6008, keyStoreName, keyStorePassword);
    }

    @Override
    public ClientRunnable createClient(SSLSocket client) throws IOException {
        return new JavaClient(client);
    }

}

package no.ntnu.idata2304.group1.server.network.listener;

import java.io.IOException;
import javax.net.ssl.SSLSocket;
import no.ntnu.idata2304.group1.server.network.clients.ClientRunnable;

public class HTTPListener extends TCPListener {

    protected HTTPListener(int port, String keyStoreName, String keyStorePassword)
            throws IOException {
        super(port, keyStoreName, keyStorePassword);
        // TODO Auto-generated constructor stub
    }

    protected HTTPListener(String keyStoreName, String keyStorePassword) throws IOException {
        super(443, keyStoreName, keyStorePassword);
        // TODO Auto-generated constructor stub
    }

    @Override
    public ClientRunnable createClient(SSLSocket client) throws IOException {
        // TODO Auto-generated method stub
        return null;
    }

}

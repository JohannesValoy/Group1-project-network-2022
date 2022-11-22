package no.ntnu.idata2304.group1.server.network.clients;

import java.io.IOException;
import javax.net.ssl.SSLSocket;
import no.ntnu.idata2304.group1.data.network.Message;

// TODO: Implement the HTTPClient class. Missing the send and receive methods.
public class HTTPClient extends ClientRunnable {

    public HTTPClient(SSLSocket socket) throws IOException {
        super(socket);
        // TODO Auto-generated constructor stub
    }

    @Override
    public void sendResponse(Message response) throws IllegalArgumentException, IOException {
        // TODO Auto-generated method stub

    }

    @Override
    protected Message getRequest() throws IllegalArgumentException, IOException {
        // TODO Auto-generated method stub
        return null;
    }

}

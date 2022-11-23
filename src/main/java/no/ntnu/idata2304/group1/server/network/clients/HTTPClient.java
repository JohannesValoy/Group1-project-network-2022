package no.ntnu.idata2304.group1.server.network.clients;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import javax.net.ssl.SSLSocket;
import no.ntnu.idata2304.group1.data.network.Message;

// TODO: Implement the HTTPClient class. Missing the send and receive methods.
public class HTTPClient extends ClientRunnable {

    private InputStream input;
    private OutputStream output;

    public HTTPClient(SSLSocket socket) throws IOException {
        super(socket);
        this.input = socket.getInputStream();
        this.output = socket.getOutputStream();
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

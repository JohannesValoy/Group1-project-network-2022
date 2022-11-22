package no.ntnu.idata2304.group1.server.network.clients;

import java.io.EOFException;
import java.io.IOException;
import java.net.Socket;
import no.ntnu.idata2304.group1.data.network.Message;

public class HTTPClient extends ClientThread {

    public HTTPClient(Socket socket) throws IOException {
        super(socket);
        // TODO Auto-generated constructor stub
    }

    @Override
    public void sendResponse(Message response) throws IllegalArgumentException, IOException {
        // TODO Auto-generated method stub

    }

    @Override
    protected Message getRequest() throws IllegalArgumentException, EOFException, IOException {
        // TODO Auto-generated method stub
        return null;
    }

}

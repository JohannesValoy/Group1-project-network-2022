package no.ntnu.idata2304.group1.server.network;

import no.ntnu.idata2304.group1.server.network.clients.ClientThread;

public class ClientTask implements Runnable {

    private ClientThread client;

    public ClientTask(ClientThread client) {
        this.client = client;
    }

    @Override
    public void run() {
        client.run();
        if (client.isClosed()) {
            ClientHandler.getInstance().removeClient(client);
        }
    }

    public ClientThread getClient() {
        return client;
    }
}


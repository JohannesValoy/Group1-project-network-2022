package no.ntnu.idata2304.group1.server.network.handlers;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Logger;
import no.ntnu.idata2304.group1.server.network.clients.ClientRunnable;

/**
 * This class is responsible for handling all the clients.
 * 
 * @author Mathias J. Kirkeby
 */
public class ClientHandler extends Thread {
    private static ClientHandler instance = null;
    private static final int TOTALTHREADS = 10;
    private final ArrayList<ClientTask> clients;
    private final ExecutorService pool;
    private static final Logger LOGGER = Logger.getLogger(ClientHandler.class.getName());

    private ClientHandler() {
        this.setName("Client Handler");
        this.clients = new ArrayList<>();
        this.pool = Executors.newFixedThreadPool(TOTALTHREADS);
    }

    /**
     * Creates a new ClientHandler and starts it
     * 
     * @return The new ClientHandler
     */
    public static ClientHandler getInstance() {
        if (instance == null) {
            instance = new ClientHandler();
            instance.start();
        }
        return instance;
    }

    /**
     * Adds a new client to the list of clients
     * 
     * @param client The client to add
     */
    public synchronized void addClient(ClientRunnable client) {
        if (client != null) {
            ClientTask clientTask = new ClientTask(client);
            clients.add(clientTask);
        }
    }

    /**
     * Removes a client from the list of clients
     * 
     * @param client The client to remove
     */
    public synchronized void removeClient(ClientTask client) {
        if (client != null) {
            clients.remove(client);
        }
    }

    @Override
    public void run() {
        while (true) {
            // While clone complains, we already know that this is a ArrayList.
            ArrayList<ClientTask> clientsCopy = null;
            try {
                clientsCopy = new ArrayList<>(this.clients);
            } catch (Exception e) {
                LOGGER.warning("Wrong with cloning the ArrayList: " + e.getMessage());
            }
            if (clients != null) {
                for (ClientTask clientTask : clientsCopy) {
                    if (clientTask != null && !clientTask.isRunning()) {
                        clientTask.setAsRunning();
                        pool.execute(clientTask);
                    }
                }
            }
        }
    }
}

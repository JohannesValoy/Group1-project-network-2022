package no.ntnu.idata2304.group1.server.network.listener;

import java.io.Closeable;
import java.io.IOException;
import java.util.logging.Logger;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSocket;
import no.ntnu.idata2304.group1.server.network.SeverSSLKeyFactory;
import no.ntnu.idata2304.group1.server.network.clients.ClientRunnable;
import no.ntnu.idata2304.group1.server.network.handlers.ClientHandler;

/**
 * Responsible for listening for new connections and creating new threads for each connection
 */
public abstract class TCPListener extends Thread implements Closeable {
    private SSLServerSocket socket;
    private ClientHandler clientHandler;

    private static final Logger LOGGER = Logger.getLogger(TCPListener.class.getName());

    /**
     * Creates a new TCP listener.
     *
     * @param port The port to listen on
     * @param keyStoreName the key store name
     * @param keyStorePassword The password to the keystore
     * @throws IOException if the socket fails to connect or the keystore is not found
     */
    protected TCPListener(int port, String keyStoreName, String keyStorePassword)
            throws IOException {
        SSLContext context = SeverSSLKeyFactory.createSSLContext(keyStoreName, keyStorePassword);
        if (context == null) {
            throw new IOException("Could not create SSL context");
        }
        SSLServerSocketFactory factory = context.getServerSocketFactory();
        this.socket = (SSLServerSocket) factory.createServerSocket(port);
        this.socket.setEnabledCipherSuites(factory.getDefaultCipherSuites());
        String[] supported = this.socket.getSupportedProtocols();
        this.socket.setEnabledProtocols(supported);
        this.clientHandler = ClientHandler.getInstance();
    }

    @Override
    public void run() {
        LOGGER.info("Starting to listening for Java Clients");
        while (true) {
            SSLSocket client = null;
            try {
                client = (SSLSocket) socket.accept();
                client.startHandshake();
                LOGGER.info("Client connected at " + client.getInetAddress().getHostAddress());
                clientHandler.addClient(createClient(client));
            } catch (IOException e) {
                if (client != null) {
                    try {
                        client.close();
                    } catch (IOException e1) {
                        LOGGER.warning("Could not close client socket");
                    }
                }
                LOGGER.warning("Could not accept client: " + e.getMessage());
            }
        }
    }

    /**
     * Create client runnable.
     *
     * @param client the client
     * @return the client runnable
     * @throws IOException the io exception
     */
    public abstract ClientRunnable createClient(SSLSocket client) throws IOException;

    @Override
    public void close() throws IOException {
        socket.close();
    }
}

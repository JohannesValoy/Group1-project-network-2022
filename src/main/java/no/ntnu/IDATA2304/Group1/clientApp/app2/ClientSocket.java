package no.ntnu.idata2304.group1.clientapp.app2;

import no.ntnu.idata2304.group1.data.network.Message;
import no.ntnu.idata2304.group1.data.network.requests.GetMessage;
import no.ntnu.idata2304.group1.data.network.responses.ErrorMessage;
import no.ntnu.idata2304.group1.data.network.responses.ResponseRoomMessage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.security.KeyStore;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManagerFactory;

/**
 * Socket class on client side to connect and read from server
 */
public class ClientSocket {

    private SSLSocket socket;
    private ObjectInputStream input;
    private ObjectOutputStream output;

    public ClientSocket(String hostname, int port) throws IOException {
        SSLContext context = createSSLContext();
        if (context == null) {
            throw new IOException("Could not create SSL context");
        }
        SSLSocketFactory factory = context.getSocketFactory();
        this.socket = (SSLSocket) factory.createSocket(hostname, port);
        this.output = new ObjectOutputStream(socket.getOutputStream());
        this.input = new ObjectInputStream(socket.getInputStream());
    }


    public Message response() throws IOException, ClassNotFoundException {
        Message messageResponse = (Message) input.readObject();

        return switch (messageResponse.getType()) {
            case OK -> (ResponseRoomMessage) messageResponse;
            case ERROR -> (ErrorMessage) messageResponse;

            default -> throw new IllegalStateException(
                    "Unexpected value: " + messageResponse.getType());
        };
    }

    public void getRoomData(List<String> rooms) throws IOException {
        output.writeObject(new GetMessage(GetMessage.Types.ROOM_TEMP, (ArrayList<String>) rooms));
    }

    private SSLContext createSSLContext() {
        SSLContext ctx = null;
        try {
            KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());

            File trustedCertsFolder = new File(
                    ClientSocket.class.getResource("trustCert").getPath().replace("%20", " "));
            for (File file : trustedCertsFolder.listFiles()) {
                if (file.getAbsolutePath().endsWith(".jks")) {
                    try (InputStream stream =
                            getClass().getResourceAsStream(file.getAbsolutePath())) {
                        trustStore.load(stream, "".toCharArray());
                    }
                }
            }
            TrustManagerFactory tmf =
                    TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            tmf.init(trustStore);
            KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
            KeyManagerFactory kmf =
                    KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
            kmf.init(keyStore, "".toCharArray());
            ctx = SSLContext.getInstance("TLS");
            ctx.init(kmf.getKeyManagers(), tmf.getTrustManagers(),
                    SecureRandom.getInstanceStrong());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ctx;
    }
}


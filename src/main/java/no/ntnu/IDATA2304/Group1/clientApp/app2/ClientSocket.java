package no.ntnu.idata2304.group1.clientapp.app2;

import no.ntnu.idata2304.group1.data.network.Message;
import no.ntnu.idata2304.group1.data.network.requests.GetMessage;
import no.ntnu.idata2304.group1.data.network.responses.ErrorMessage;
import no.ntnu.idata2304.group1.data.network.responses.ResponseRoomMessage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.security.GeneralSecurityException;
import java.security.KeyStore;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;
import javax.net.SocketFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

/**
 * Socket class on client side to connect and read from server
 * 
 */
// TODO: Remove the ssl stuff into a separate class
public class ClientSocket {

    private SSLSocket socket;
    private ObjectInputStream input;
    private ObjectOutputStream output;

    public ClientSocket(String hostname, int port) throws IOException {
        SocketFactory factory = SSLSocketFactory.getDefault();
        this.socket = (SSLSocket) factory.createSocket(hostname, port);
        this.socket.startHandshake();
        this.output = new ObjectOutputStream(socket.getOutputStream());
        this.input = new ObjectInputStream(socket.getInputStream());
    }

    public ClientSocket(String hostname, int port, String folderForCustomCert) throws IOException {
        SSLContext context = createTrustStore(folderForCustomCert);
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

    private SSLContext createTrustStore(String path) {
        SSLContext ctx = null;
        try {
            KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
            trustStore.load(null, null);
            CertificateFactory cf = CertificateFactory.getInstance("X.509");
            File trustedCertsFolder = new File(path);
            if (trustedCertsFolder.isDirectory()) {
                for (File file : trustedCertsFolder.listFiles()) {
                    if (file.getAbsolutePath().endsWith(".cer")) {
                        try (InputStream stream = new FileInputStream(file)) {
                            trustStore.setCertificateEntry(file.getName(),
                                    cf.generateCertificate(stream));
                        }
                    }
                }
            }
            TrustManagerFactory trustFactory =
                    TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            addDefaultRootCaCertificates(trustStore);
            trustFactory.init(trustStore);
            ctx = SSLContext.getInstance("TLS");
            ctx.init(null, trustFactory.getTrustManagers(), null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ctx;
    }

    private static void addDefaultRootCaCertificates(KeyStore trustStore)
            throws GeneralSecurityException {
        TrustManagerFactory trustManagerFactory =
                TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
        // Loads default Root CA certificates (generally, from JAVA_HOME/lib/cacerts)
        trustManagerFactory.init((KeyStore) null);
        for (TrustManager trustManager : trustManagerFactory.getTrustManagers()) {
            if (trustManager instanceof X509TrustManager) {
                for (X509Certificate acceptedIssuer : ((X509TrustManager) trustManager)
                        .getAcceptedIssuers()) {
                    trustStore.setCertificateEntry(acceptedIssuer.getIssuerX500Principal() + "-"
                            + acceptedIssuer.getSerialNumber(), acceptedIssuer);
                }
            }
        }
    }
}


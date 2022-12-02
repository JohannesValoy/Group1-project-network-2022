package no.ntnu.idata2304.group1.data.network;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.security.GeneralSecurityException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * A class to implement custom trust store for SSL connections It is used to connect to a server
 * with a self-signed certificate.
 * Since the server don't require a client certificate, we don't need to implement a KeyManager.
 */
public class SSLTrustFactory {
    private SSLTrustFactory() {}

    private static final Logger logger = Logger.getLogger(SSLTrustFactory.class.getName());

    /**
     * Creates a trust store from a folder containing a certificate. Only accepts .cer files.
     *
     * @param path A folder containing a certificate or null if none is provided
     * @return A SSLContext with the trust store or null if none is provided
     */
    public static SSLContext createTrustStore(String path) {
        SSLContext ctx = null;
        try {
            KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
            trustStore.load(null, null);

            TrustManagerFactory trustFactory =
                    TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            addDefaultRootCaCertificates(trustStore);
            if (path != null) {
                addCustomRootCertificates(trustStore, path);
            }
            trustFactory.init(trustStore);
            ctx = SSLContext.getInstance("TLS");
            ctx.init(null, trustFactory.getTrustManagers(), null);
        } catch (KeyStoreException | CertificateException e) {
            logger.log(Level.SEVERE, "Could not create trust store", e);
        } catch (GeneralSecurityException e) {
            logger.log(Level.SEVERE, "Could not create SSL context", e);
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Could not load default root CA certificates", e);
        }

        return ctx;
    }

    /**
     * Gets the default root CA certificates from the JVM and adds them to the trust store.
     * 
     * @param trustStore The trust store to add the certificates to.
     * @throws GeneralSecurityException If the certificates could not be added to the trust store.
     */
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

    /**
     * Adds custom root CA certificates to the trust store.
     * 
     * @param trustStore The trust store to add the certificates to.
     * @param path The folder the certificates are located in.
     * @throws GeneralSecurityException If the certificates could not be added to the trust store.
     */
    private static void addCustomRootCertificates(KeyStore trustStore, String path)
            throws GeneralSecurityException {
        CertificateFactory cf = CertificateFactory.getInstance("X.509");
        File trustedCertsFolder = new File(path);
        if (trustedCertsFolder.exists()) {
            ArrayList<File> files = new ArrayList<>();
            if (trustedCertsFolder.isDirectory()) {
                files.addAll(Arrays.asList(trustedCertsFolder.listFiles()));

            } else {
                files.add(trustedCertsFolder);
            }
            for (File file : files) {
                try (InputStream stream = new FileInputStream(file)) {
                    trustStore.setCertificateEntry(file.getName(), cf.generateCertificate(stream));
                } catch (FileNotFoundException e) {
                    logger.log(Level.SEVERE, "File not found", e);
                } catch (IOException e) {
                    logger.log(Level.SEVERE, "IO Exception", e);
                }
            }
        }
    }
}

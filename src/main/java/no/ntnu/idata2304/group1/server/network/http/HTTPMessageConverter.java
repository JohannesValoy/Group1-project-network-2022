package no.ntnu.idata2304.group1.server.network.http;

/**
 * The type Http message converter.
 */
public class HTTPMessageConverter {
    private HTTPMessageConverter() {}

    /**
     * Convert http message.
     *
     * @param message the message
     * @return the http message
     */
    public static HTTPMessage convert(String message) {
        if (message == null) {
            throw new IllegalArgumentException("Message can't be null");
        }
        if (message.startsWith("HTTP")) {
            return new HTTPResponse(message);
        } else {
            return new HTTPRequest(message);
        }
    }
}

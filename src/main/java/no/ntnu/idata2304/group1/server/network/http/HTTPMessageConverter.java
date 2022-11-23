package no.ntnu.idata2304.group1.server.network.http;

public class HTTPMessageConverter {
    private HTTPMessageConverter() {}

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

package no.ntnu.idata2304.group1.server.network.http;

/**
 * The type Http request.
 */
public class HTTPRequest extends HTTPMessage {

    private String method;

    private String path;

    /**
     * Instantiates a new Http request.
     *
     * @param message the message
     */
    public HTTPRequest(String message) {
        super(message);
        String[] startLine = message.trim().split("\n")[0].split(" ");
        method = startLine[0];
        path = startLine[1];
    }

    /**
     * Returns the method of the request
     *
     * @return method
     */
    public String getMethod() {
        return method;
    }

    /**
     * Returns the path of the request
     *
     * @return the path
     */
    public String getPath() {
        return path;
    }

}

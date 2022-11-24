package no.ntnu.idata2304.group1.server.network.http;

/**
 * The HTTPResponse class represents a HTTP response
 */
public class HTTPResponse extends HTTPMessage {
    private int statusCode;
    private String statusMessage;

    /**
     * Creates a new HTTPResponse from a message
     *
     * @param message - the message to create the response from
     */
    public HTTPResponse(String message) {
        super(message);
        String[] startLine = message.trim().split("\n")[0].split(" ");
        statusCode = Integer.parseInt(startLine[1]);
        statusMessage = startLine[2];
    }

    /**
     * Instantiates a new Http response.
     *
     * @param statusCode    the status code
     * @param statusMessage the status message
     */
    public HTTPResponse(int statusCode, String statusMessage) {
        super("");
        this.statusCode = statusCode;
        this.statusMessage = statusMessage;
    }

    /**
     * Returns the status code of the response
     *
     * @return the status code
     */
    public int getStatusCode() {
        return statusCode;
    }

    /**
     * Returns the status message of the response
     *
     * @return the status message
     */
    public String getStatusMessage() {
        return statusMessage;
    }
}

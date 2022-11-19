package no.ntnu.idata2304.group1.data.network.http;

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

    public HTTPResponse(int statuscode, String statusMessage) {
        super("");
        this.statusCode = statuscode;
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

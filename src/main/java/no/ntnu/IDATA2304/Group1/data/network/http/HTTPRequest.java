package no.ntnu.idata2304.group1.data.network.http;

public class HTTPRequest extends HTTPMessage {

    private String method;

    private String path;

    public HTTPRequest(String message) {
        super(message);
        String[] startLine = message.trim().split("\n")[0].split(" ");
        method = startLine[0];
        path = startLine[1];
    }

    /**
     * Returns the method of the request
     * 
     * @return
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

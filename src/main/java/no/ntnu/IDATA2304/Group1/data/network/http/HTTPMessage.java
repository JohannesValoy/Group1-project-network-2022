package no.ntnu.idata2304.group1.data.network.http;

import java.util.HashMap;
import java.util.Scanner;

public abstract class HTTPMessage {

    private String httpVersion;
    private HashMap<String, String> header;
    private String body;

    protected HTTPMessage(String message) {
        if (message == null) {
            throw new IllegalArgumentException("Message can't be null");
        }
        try (Scanner messageScan = new Scanner(message)) {
            String startLine = messageScan.nextLine();
            boolean isHeader = true;
            StringBuilder bodyBuilder = new StringBuilder();
            while (messageScan.hasNext()) {
                String nextLine = messageScan.nextLine();
                if (isHeader && nextLine.isBlank()) {
                    isHeader = false;
                    nextLine = messageScan.nextLine();
                }
                if (isHeader) {
                    String[] headerLine = nextLine.split(":");
                    header.put(headerLine[0], headerLine[1]);
                } else {
                    bodyBuilder.append(nextLine);
                }
            }
            this.body = bodyBuilder.toString();
        }
    }

    /**
     * Returns the HeaderValue given a key
     * 
     * @param key - the key to search for
     * @return the value of the header
     * @throws IllegalArgumentException if the key is null, empty or is not within the message
     */
    public final String getHeaderInformation(String key) throws IllegalArgumentException {
        if (key == null || key.isBlank()) {
            throw new IllegalArgumentException("The key is invalid");
        }
        if (!header.containsKey(key)) {
            throw new IllegalArgumentException("The key is not within the HTTPMessage");
        }
        return header.get(key);
    }

    /**
     * Returns the body as a string
     * 
     * @return String - the body
     */
    public final String getBody() {
        return body;
    }

    /**
     * Returns the HTTP version
     * 
     * @return String - the HTTP version
     */
    public final String getHttpVersion() {
        return httpVersion;
    }
}

package no.ntnu.idata2304.group1.data.requests;

public class ErrorMessage extends Message {
    private String errorMessage;

    public ErrorMessage(String message) {
        super(Message.Types.ERROR);
        this.errorMessage = message;
    }

    public String getMessage() {
        return errorMessage;
    }
}

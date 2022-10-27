package no.ntnu.idata2304.group1.data.requests;

public abstract class Message {
    public enum Types {
        GET(), ADD(), REMOVE(), UPDATE(), ERROR(), OK();

        private Types() {}

    }

    private Message.Types type;

    protected Message(Message.Types type) {
        this.type = type;
    }

    public Message.Types getType() {
        return type;
    }

}

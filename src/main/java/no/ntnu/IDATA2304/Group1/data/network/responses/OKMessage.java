package no.ntnu.idata2304.group1.data.network.responses;

import no.ntnu.idata2304.group1.data.network.Message;

public class OKMessage extends Message {

    public OKMessage() {
        super(Message.Types.OK);
    }
}

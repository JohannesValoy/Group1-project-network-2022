package no.ntnu.idata2304.group1.server.requests;

import org.junit.jupiter.api.Test;
import no.ntnu.idata2304.group1.data.requests.GetMessage;
import no.ntnu.idata2304.group1.data.requests.Message;

public class RequestHandlerTest {
    @Test
    public void testRequestHandler() {
        RequestHandler handler = new RequestHandler();
        GetMessage request = new GetMessage(GetMessage.Types.ROOM_TEMP, "C220");
        Message message = handler.getResponse(request);
        System.out.println(message.getType());
    }
}

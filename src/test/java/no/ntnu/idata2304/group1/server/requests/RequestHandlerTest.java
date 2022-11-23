package no.ntnu.idata2304.group1.server.requests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import no.ntnu.idata2304.group1.data.network.Message;
import no.ntnu.idata2304.group1.data.network.requests.get.GetLogsMessage;
import no.ntnu.idata2304.group1.server.requests.RequestHandler;

public class RequestHandlerTest {

    @Test
    public void testRequestHandler() {
        RequestHandler handler = new RequestHandler();
        GetLogsMessage request = new GetLogsMessage(GetLogsMessage.Logs.TEMPERATURE, "C220");
        Message message = handler.getResponse(request);
        assertEquals(message.getType(), Message.Types.OK);


    }
}

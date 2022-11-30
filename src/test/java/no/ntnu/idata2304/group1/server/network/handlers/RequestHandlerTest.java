package no.ntnu.idata2304.group1.server.network.handlers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import no.ntnu.idata2304.group1.data.Sensor;
import no.ntnu.idata2304.group1.data.network.Message;
import no.ntnu.idata2304.group1.data.network.requests.get.GetLogsMessage;

public class RequestHandlerTest {

    @Test
    public void testRequestHandler() {
        GetLogsMessage request = new GetLogsMessage(Sensor.Types.TEMPERATURE, "C220");
        Message message = RequestHandler.getResponse(request);
        assertEquals(message.getType(), Message.Types.OK);


    }
}

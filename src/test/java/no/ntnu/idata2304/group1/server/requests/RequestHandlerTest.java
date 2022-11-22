package no.ntnu.idata2304.group1.server.requests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import java.sql.SQLException;
import org.junit.jupiter.api.Test;
import no.ntnu.idata2304.group1.data.network.Message;
import no.ntnu.idata2304.group1.data.network.requests.GetMessage;
import no.ntnu.idata2304.group1.server.database.DBConnector;
import no.ntnu.idata2304.group1.server.database.SQLConnectorTest;

public class RequestHandlerTest {

    @Test
    public void testRequestHandler() {
        DBConnector sql = null;
        try {
            sql = SQLConnectorTest.getTestConnector();
            RequestHandler handler = new RequestHandler();
            GetMessage request = new GetMessage(GetMessage.Types.ROOM_TEMP, "C220");
            Message message = handler.getResponse(request);
            assertEquals(message.getType(), Message.Types.OK);
        } catch (SQLException e) {
            fail("There is a problem with the testConnector");
        }


    }
}

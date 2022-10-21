package no.ntnu.idata2304.group1.server.requests;

import java.sql.SQLException;
import org.json.JSONException;
import org.json.JSONObject;
import no.ntnu.idata2304.group1.server.database.DBConnector;

public class RequestHandler {

    private DBConnector connector;

    public RequestHandler() {
        this.connector = new DBConnector();
    }

    public String getResponse(String clientRequest) {
        String response;
        try {
            JSONObject requestJSON = new JSONObject(clientRequest);
            String sqlQuery = createSQLQuery(requestJSON);
            connector.executeQuery(sqlQuery);
            response = "";
        } catch (JSONException e) {
            response =
                    "{ \"code\":\"error\", \"message\" : \"Is not a JSON message or did not contain a command key.\"}";
        } catch (IllegalArgumentException e) {
            response =
                    "{ \"code\":\"error\",\"message\" : \"The parameters were either invalid or you didn't assign any. Please check the documentation for the commands\"}";
        } catch (NotImplementedException e) {
            response =
                    "{ \"code\":\"error\",\"message\" : \"'This command is not implemented. Please check the documentation for commands.'\"}";
        } catch (SQLException e) {
            response =
                    "{ \"code\":\"error\",\"message\" : \"A unexpected error accessing your information. Please try again.\"}";
        }
        return response;
    }

    private String createSQLQuery(JSONObject object) throws NotImplementedException, JSONException {
        throw new NotImplementedException();
    }
}

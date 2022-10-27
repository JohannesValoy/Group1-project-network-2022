package no.ntnu.idata2304.group1.server.requests;

import java.sql.ResultSet;
import java.sql.SQLException;
import no.ntnu.idata2304.group1.data.requests.ErrorMessage;
import no.ntnu.idata2304.group1.data.requests.GetMessage;
import no.ntnu.idata2304.group1.data.requests.Message;
import no.ntnu.idata2304.group1.server.database.DBConnector;
import no.ntnu.idata2304.group1.server.database.SQLCommandFactory;
import no.ntnu.idata2304.group1.data.requests.ResponseMessage;

public class RequestHandler {

    // TODO: Implement JSON support for requests that is not from Java

    private DBConnector connector;
    // TODO: Add this when the update/add request is implemented
    // private Role role;

    public RequestHandler() {
        this.connector = new DBConnector();
    }

    public RequestHandler(DBConnector connector) {
        this.connector = connector;
    }

    // TODO: Implement Login
    // TODO: Work like your life deppends on it
    /**
     * @param request
     * @return
     */
    public Message getResponse(Message request) {
        Message response = null;
        try {
            switch (request.getType()) {
                case GET:
                    response = handleGet((GetMessage) request);
                    break;
                case ERROR:
                    response = handleError((ErrorMessage) request);
                    break;
                case OK:
                    response = handleOk((ResponseMessage) request);
                    break;
                default:
                    response = new ResponseMessage("Unknown command");
                    break;
            }
        } catch (IllegalArgumentException e) {
            response = new ErrorMessage("Invalid request");
        } catch (SQLException e) {
            response = new ErrorMessage("Database error");
        }

        return response;
    }


    private ResponseMessage handleOk(ResponseMessage request) {
        return null;
    }

    /**
     * @param request
     * @return
     */
    private Message handleGet(GetMessage request) throws IllegalArgumentException, SQLException {
        String sqlQuery = "";
        ResponseMessage response = null;
        switch (request.getCommand()) {
            case ROOM_TEMP:
                sqlQuery = SQLCommandFactory.getTemperature(request.getRooms());
                ResultSet result = connector.executeQuery(sqlQuery);
                response = new ResponseMessage("Temperature data");
                while (result.next()) {
                    response.addData(result.getString("room"), result.getDouble("temperature"));
                }
                break;
            case ROOM_HUMIDITY:
                // TODO: Implement this
                // return getRoomHumidity();
            default:
                throw new IllegalArgumentException("Unknown Get Command");
        }
        return response;
    }

    private ResponseMessage handleError(ErrorMessage request) {
        return null;
    }

    // TODO: Implement this

    // private String createSQLQuery(JSONObject object)
    // throws NotImplementedException, IllegalArgumentException {
    // String sqlQuery;
    // String command;
    // try {
    // command = object.getString("command");
    // } catch (Exception e) {
    // throw new IllegalArgumentException(
    // "This JSON object does not contain a command keyword.");
    // }
    // switch (command.toLowerCase()) {
    // case "login":

    // break;
    // case "getroomtemp":
    // sqlQuery = GetMessage.getRoomTemprature(object);
    // ResultSet result;
    // try {
    // result = connector.executeQuery(sqlQuery);
    // } catch (SQLException e) {
    // throw new IllegalArgumentException(
    // "Had problems contacting the database, please try again.");
    // }
    // break;
    // default:
    // throw new IllegalArgumentException("This is not a recognized command keyword");
    // }
    // return sqlQuery;
    // }
}

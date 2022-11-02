package no.ntnu.idata2304.group1.server.requests;

import java.sql.ResultSet;
import java.sql.SQLException;
import no.ntnu.idata2304.group1.data.RoomRecord;
import no.ntnu.idata2304.group1.data.requests.ErrorMessage;
import no.ntnu.idata2304.group1.data.requests.GetMessage;
import no.ntnu.idata2304.group1.data.requests.Message;
import no.ntnu.idata2304.group1.server.database.DBConnector;
import no.ntnu.idata2304.group1.server.database.SQLCommandFactory;
import no.ntnu.idata2304.group1.data.requests.ResponseMessage;

/**
 * A class for handling requests from the client
 */
public class RequestHandler {


    // TODO: Add this when the update/add request is implemented
    // TODO: Implement Login

    private DBConnector connector;

    /**
     * Creates a new RequestHandler
     */
    public RequestHandler() {
        this.connector = new DBConnector();
    }

    /**
     * Creates a new request handler with a given database connector
     * 
     * @param connector The database connector to use
     */
    public RequestHandler(DBConnector connector) {
        this.connector = connector;
    }


    /**
     * Handles a given request and returns a response
     * 
     * @param request The request to handle
     * @return Message object containing the response
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

    /**
     * Handles a OK request
     * 
     * @param request The request to handle
     * @return The response
     */
    // TODO: Implement this method
    private ResponseMessage handleOk(ResponseMessage request) {
        return null;
    }

    /**
     * Handles a GET request
     * 
     * @param request The request to handle
     * @return Message object containing the response
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
                    response.addData(result.getString("room"), new RoomRecord(
                            result.getTimestamp("timestamp"), result.getDouble("temperature")));
                }
                break;
            case ROOM_HUMIDITY:
                // TODO: Implement this
            default:
                throw new IllegalArgumentException("Unknown Get Command");
        }
        return response;
    }

    /**
     * Handles an ERROR request
     * 
     * @param request The request to handle
     * @return The response
     */
    // TODO: Implement this method
    private ResponseMessage handleError(ErrorMessage request) {
        return null;
    }
}

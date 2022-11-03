package no.ntnu.idata2304.group1.server.requests;

import java.sql.ResultSet;
import java.sql.SQLException;
import no.ntnu.idata2304.group1.data.networkpackages.Message;
import no.ntnu.idata2304.group1.data.networkpackages.requests.AddMessage;
import no.ntnu.idata2304.group1.data.networkpackages.requests.GetMessage;
import no.ntnu.idata2304.group1.data.networkpackages.requests.UpdateMessage;
import no.ntnu.idata2304.group1.data.networkpackages.responses.ErrorMessage;
import no.ntnu.idata2304.group1.data.networkpackages.responses.ResponseRoomMessage;
import no.ntnu.idata2304.group1.server.database.DBConnector;
import no.ntnu.idata2304.group1.server.database.SQLCommandFactory;
import no.ntnu.idata2304.group1.server.database.SQLConverter;

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
        if (connector == null) {
            throw new IllegalArgumentException("The connector cannot be null");
        }
        this.connector = connector;
    }


    /**
     * Handles a given request and returns a response
     * 
     * @param request The request to handle
     * @return Message object containing the response
     */
    public Message getResponse(Message request) {
        if (request == null) {
            throw new IllegalArgumentException("The request cannot be null");
        }
        Message response = null;
        try {
            switch (request.getType()) {
                case GET:
                    response = handleGet((GetMessage) request);
                    break;
                case ADD:
                    response = handleAdd((AddMessage) request);
                    break;
                case UPDATE:
                    response = handleUpdate((UpdateMessage) request);
                    break;
                default:
                    response = new ErrorMessage("Unknown command");
                    break;
            }
        } catch (IllegalArgumentException e) {
            response = new ErrorMessage(e.getMessage());
        } catch (SQLException e) {
            response = new ErrorMessage("Database error");
        }

        return response;
    }

    private Message handleUpdate(UpdateMessage request) {
        return null;
    }

    private Message handleAdd(AddMessage request) {
        return null;
    }


    /**
     * Handles a GET request
     * 
     * @param request The request to handle
     * @return Message object containing the response
     */
    private Message handleGet(GetMessage request) throws IllegalArgumentException, SQLException {
        if (request == null) {
            throw new IllegalArgumentException("The request cannot be null");
        }
        String sqlQuery = "";
        ResponseRoomMessage response = null;
        switch (request.getCommand()) {
            case ROOM_TEMP:
                sqlQuery = SQLCommandFactory.getTemperature(request.getRooms());
                ResultSet result = connector.executeQuery(sqlQuery);
                response = new ResponseRoomMessage(SQLConverter.getRoomLogResults(result));
                break;
            case ROOM_HUMIDITY:
                // TODO: Implement this
            default:
                throw new IllegalArgumentException("Unknown Get Command");
        }
        return response;
    }
}

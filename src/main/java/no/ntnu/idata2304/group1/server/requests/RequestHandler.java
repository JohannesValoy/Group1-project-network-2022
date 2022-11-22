package no.ntnu.idata2304.group1.server.requests;

import java.sql.ResultSet;
import java.sql.SQLException;
import javax.xml.catalog.Catalog;
import no.ntnu.idata2304.group1.data.network.Message;
import no.ntnu.idata2304.group1.data.network.requests.AddMessage;
import no.ntnu.idata2304.group1.data.network.requests.GetMessage;
import no.ntnu.idata2304.group1.data.network.requests.UpdateMessage;
import no.ntnu.idata2304.group1.data.network.responses.ErrorMessage;
import no.ntnu.idata2304.group1.data.network.responses.OKMessage;
import no.ntnu.idata2304.group1.data.network.responses.ResponseRoomMessage;
import no.ntnu.idata2304.group1.server.database.DBConnectorPool;
import no.ntnu.idata2304.group1.server.database.SQLCommandFactory;
import no.ntnu.idata2304.group1.server.database.SQLConverter;

/**
 * A class for handling requests from the client
 */
public class RequestHandler {


    // TODO: Add this when the update request is implemented
    // TODO: Implement Login

    private DBConnectorPool connector;

    /**
     * Creates a new RequestHandler
     */
    public RequestHandler() {
        this.connector = DBConnectorPool.getInstance();
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
            response = switch (request.getType()) {
                case GET -> handleGet((GetMessage) request);
                case UPDATE -> handleUpdate((UpdateMessage) request);
                case ADD -> handleAdd((AddMessage) request);
                default -> new ErrorMessage("Unknown command");
            };
        } catch (IllegalArgumentException e) {
            response = new ErrorMessage(e.getMessage());
        } catch (SQLException e) {
            response = new ErrorMessage("Database error");
        }
        return response;
    }

    private Message handleUpdate(UpdateMessage request) throws SQLException {
        return new OKMessage();
    }

    private Message handleAdd(AddMessage request) throws SQLException {
        switch (request.getCommand()) {
            case LOG:
                if (!isValidKey(request.getApiKey())) {
                    throw new IllegalArgumentException("Invalid key");
                }
                String sqlQuery = SQLCommandFactory.addLog(request.getApiKey(), request.getValue());
                connector.executeQuery(sqlQuery);
                break;
        }
        return new OKMessage();

    }


    private boolean isValidKey(String apiKey) throws SQLException, IllegalArgumentException {
        if (apiKey == null || apiKey.isBlank() || apiKey.contains(" ")) {
            throw new IllegalArgumentException("The key is invalid");
        }
        String sql = SQLCommandFactory.checkNodeKey(apiKey);
        ResultSet result = connector.executeQuery(sql);
        return result.next();
    }

    /**
     * Handles a GET request
     * 
     * @param request The request to handle
     * @return Message object containing the response
     */
    private Message handleGet(GetMessage request) throws IllegalArgumentException, SQLException {
        ResponseRoomMessage response = null;
        switch (request.getCommand()) {
            case ROOM_TEMP:
                String sqlQuery = SQLCommandFactory.getTemperature(request.getRooms());
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

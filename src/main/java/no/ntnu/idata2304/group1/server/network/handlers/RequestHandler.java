package no.ntnu.idata2304.group1.server.network.handlers;

import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;
import no.ntnu.idata2304.group1.data.Room;
import no.ntnu.idata2304.group1.data.network.Message;
import no.ntnu.idata2304.group1.data.network.requests.UpdateMessage;
import no.ntnu.idata2304.group1.data.network.requests.add.AddMessage;
import no.ntnu.idata2304.group1.data.network.requests.get.GetLogsMessage;
import no.ntnu.idata2304.group1.data.network.requests.get.GetMessage;
import no.ntnu.idata2304.group1.data.network.requests.get.GetRoomsMessage;
import no.ntnu.idata2304.group1.data.network.responses.DataMessage;
import no.ntnu.idata2304.group1.data.network.responses.ErrorMessage;
import no.ntnu.idata2304.group1.data.network.responses.OKMessage;
import no.ntnu.idata2304.group1.server.database.SQLCommandFactory;

/**
 * A class for handling requests from the client
 */
public class RequestHandler {


    // TODO: Implement Login


    /**
     * Creates a new RequestHandler
     */
    private RequestHandler() {}


    /**
     * Handles a given request and returns a response
     *
     * @param request The request to handle
     * @return Message object containing the response
     */
    public static Message getResponse(Message request) {
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

    private static Message handleUpdate(UpdateMessage request) throws SQLException {
        return new OKMessage();
    }

    private static Message handleAdd(AddMessage request) throws SQLException {
        switch (request.getCommand()) {
            case LOG:
                if (!SQLCommandFactory.checkNodeKey(request.getApiKey())) {
                    throw new IllegalArgumentException("Invalid key");
                }
                if (!SQLCommandFactory.addLog(request.getApiKey(), request.getValue())) {
                    throw new IllegalArgumentException("Invalid log");
                }
                break;
            default:
                throw new IllegalArgumentException("Unknown command");

        }
        return new OKMessage();

    }

    /**
     * Handles a GET request
     * 
     * @param request The request to handle
     * @return Message object containing the response
     */
    private static Message handleGet(GetMessage request)
            throws IllegalArgumentException, SQLException {
        DataMessage response = null;
        switch (request.getCommand()) {
            case DATA:
                GetLogsMessage logsRequest = (GetLogsMessage) request;
                Iterator<String> it = logsRequest.getRooms();
                if (!it.hasNext()) {
                    it = SQLCommandFactory.getRooms(null).iterator();
                }
                List<Room> rooms = SQLCommandFactory.getRoomData(it, logsRequest.getLimit(),
                        logsRequest.getFrom(), logsRequest.getTo(), logsRequest.getDataType());
                response = new DataMessage(rooms);
                break;
            case ROOMS:
                GetRoomsMessage convertedRequest = (GetRoomsMessage) request;
                List<String> roomNames = SQLCommandFactory.getRooms(convertedRequest.getFilter());
                response = new DataMessage(String.class, roomNames);
                break;
            case Sensor:
                // TODO: Implements this
                break;
            default:
                throw new IllegalArgumentException("Unknown Get Command");
        }
        return response;
    }
}

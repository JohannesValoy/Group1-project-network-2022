package no.ntnu.idata2304.group1.data.network.requests.get;

/**
 * The type Get rooms message.
 */
public class GetRoomsMessage extends GetMessage {

    /**
     * The Filter.
     */
    String filter;

    /**
     * Instantiates a new Get rooms message.
     *
     * @param roomFilter the room filter
     */
    public GetRoomsMessage(String roomFilter) {
        super(GetMessage.DataTypes.ROOMS);
        if (roomFilter == null) {
            this.filter = "";
        } else {
            this.filter = roomFilter;
        }
    }

    /**
     * Gets filter.
     *
     * @return the filter
     */
    public String getFilter() {
        return filter;
    }
}

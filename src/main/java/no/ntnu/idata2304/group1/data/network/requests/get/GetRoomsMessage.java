package no.ntnu.idata2304.group1.data.network.requests.get;

public class GetRoomsMessage extends GetMessage {

    String filter;

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

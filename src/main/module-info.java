module no.ntnu.idata2304.group1.server {
    requires org.json;
    requires java.sql;

    opens no.ntnu.idata2304.group1.server.network;

    exports no.ntnu.idata2304.group1.server.network;

    opens no.ntnu.idata2304.group1.server.messages;
    opens no.ntnu.idata2304.group1.server.requests.access;
}

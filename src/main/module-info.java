module no.ntnu.idata2304.group1.server {
    requires org.json;
    requires java.sql;

    opens no.ntnu.idata2304.group1.server.network;
    opens no.ntnu.idata2304.group1.server.database;
    opens no.ntnu.idata2304.group1.server.messages;

    exports no.ntnu.idata2304.group1.server.network;

}

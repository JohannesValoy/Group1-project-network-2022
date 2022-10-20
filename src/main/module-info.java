module no.ntnu.idata2304.group1.server{
    requires org.json;
    requires java.sql;
    opens no.ntnu.idata2304.group1.server;
    opens no.ntnu.idata2304.group1.server.communicator;
    exports no.ntnu.idata2304.group1.server.communicator;
    opens no.ntnu.idata2304.group1.server.messages;
}

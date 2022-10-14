module no.ntnu.IDATA2304.Group1.server {
    requires transitive org.json;
    opens no.ntnu.IDATA2304.Group1.server;
    opens no.ntnu.IDATA2304.Group1.server.communicator;
    exports no.ntnu.IDATA2304.Group1.server.communicator;
    opens no.ntnu.IDATA2304.Group1.server.messages;
}

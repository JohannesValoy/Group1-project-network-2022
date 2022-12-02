module Group1NetworkProject {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires org.json;

    opens no.ntnu.idata2304.group1.clientapp.app.ui to javafx.fxml;

    opens no.ntnu.idata2304.group1.clientapp.app.network;

    exports no.ntnu.idata2304.group1.clientapp.app.network;

    exports no.ntnu.idata2304.group1.clientapp.app.ui to javafx.graphics;
    exports no.ntnu.idata2304.group1.data;
    exports no.ntnu.idata2304.group1.data.network;
    opens no.ntnu.idata2304.group1.data.network;
    exports no.ntnu.idata2304.group1.clientapp.app;
    opens no.ntnu.idata2304.group1.clientapp.app;
}

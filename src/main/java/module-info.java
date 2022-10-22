module no.ntnu.idata2304.Group1.clientApp.logic{
    requires javafx.controls;
    requires javafx.fxml;
    opens no.ntnu.idata2304.Group1.clientApp.app2.ui to javafx.fxml;
    exports no.ntnu.idata2304.Group1.clientApp.app2 to javafx.graphics;
    exports no.ntnu.idata2304.Group1.clientApp.app2.logic;
    opens no.ntnu.idata2304.Group1.clientApp.app2 to javafx.fxml;
    exports no.ntnu.idata2304.Group1.clientApp.app2.ui to javafx.graphics;
}

module no.ntnu.idata2306.group1.server{
    requires org.json;
    requires java.sql;
}
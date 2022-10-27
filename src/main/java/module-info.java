module no.ntnu.idata2304.group1.clientApp.logic{
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires org.json;
    opens no.ntnu.idata2304.group1.clientApp.app2.ui to javafx.fxml;
    exports no.ntnu.idata2304.group1.clientApp.app2 to javafx.graphics;
    exports no.ntnu.idata2304.group1.clientApp.app2.logic;
    opens no.ntnu.idata2304.group1.clientApp.app2 to javafx.fxml;
    exports no.ntnu.idata2304.group1.clientApp.app2.ui to javafx.graphics;
}
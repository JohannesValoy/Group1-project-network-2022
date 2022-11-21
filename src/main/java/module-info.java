module Group1NetworkProject {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires org.json;

    opens no.ntnu.idata2304.group1.clientapp.app2.ui to javafx.fxml;

    opens no.ntnu.idata2304.group1.clientapp.app2 to javafx.fxml;

    exports no.ntnu.idata2304.group1.clientapp.app2.ui to javafx.graphics;
    exports no.ntnu.idata2304.group1.data;
}

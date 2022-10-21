module no.ntnu.IDATA2304.Group1.clientApp.logic{
    requires javafx.controls;
    requires javafx.fxml;
    opens no.ntnu.IDATA2304.Group1.clientApp.app2.ui to javafx.fxml;
    exports no.ntnu.IDATA2304.Group1.clientApp.app2 to javafx.graphics;
    exports no.ntnu.IDATA2304.Group1.clientApp.app2.logic;
    opens no.ntnu.IDATA2304.Group1.clientApp.app2 to javafx.fxml;
    exports no.ntnu.IDATA2304.Group1.clientApp.app2.ui to javafx.graphics;
}

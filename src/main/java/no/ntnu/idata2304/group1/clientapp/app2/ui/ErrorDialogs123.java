package no.ntnu.idata2304.group1.clientapp.app2.ui;

import java.io.IOException;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class ErrorDialogs123{
    private ErrorDialogs123(){}



public static void couldNotConnectAlert(IOException e) {
    Alert alert = new Alert(AlertType.ERROR);
    alert.setTitle("Error");
    alert.setHeaderText("Could not connect to server");
    alert.setContentText(e.getMessage()+ " - Make sure the server is running and all the details entered are correct");
    alert.showAndWait();
    System.exit(0);
}

public static void couldNotUpdateRoom(ClassNotFoundException e){
    new Alert(AlertType.ERROR, e.getMessage() + " - Could not update room data").showAndWait();
    System.exit(0);
}
}
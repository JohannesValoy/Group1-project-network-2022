package no.ntnu.idata2304.group1.clientapp.app.ui;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

/**
 * The type Error dialogs.
 */
public class ErrorDialogs {
    private ErrorDialogs(){}


    /**
     * Could not connect alert.
     *
     * @param e the Exception
     */
    public static void couldNotConnectAlert(Exception e) {
    Alert alert = new Alert(AlertType.ERROR);
    alert.setTitle("Error");
    alert.setHeaderText("Could not connect to server");
    alert.setContentText(e.getMessage()+ " - Make sure the server is running and all the details entered are correct");
    alert.showAndWait();
    System.exit(0);
}

    /**
     * Could not update room.
     *
     * @param e the ClassNotFoundException
     */
    public static void couldNotUpdateRoom(ClassNotFoundException e){
    new Alert(AlertType.ERROR, e.getMessage() + " - Could not update room data").showAndWait();
    System.exit(0);
}

    /**
     * Invalid port number.
     *
     * @param message the port number message
     */
    public static void invalidPortNumber(String message) {
    new Alert(AlertType.ERROR, "Invalid port number" + " " + message).showAndWait();
    System.exit(0);
}

    /**
     * General error.
     *
     * @param e the Exception
     */
    public static void generalError(Exception e) {
    new Alert(AlertType.ERROR, e.getMessage()).showAndWait();
    System.exit(0);
}
}
package no.ntnu.idata2304.group1.clientapp.app.ui;

import java.io.IOException;
import java.util.Optional;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Pair;
import no.ntnu.idata2304.group1.clientapp.app.network.ClientSocket;

public class MultiInputDialog extends Dialog {

    String certPath;

    static Dialog<Pair<String, String>> dialog;

    public MultiInputDialog() {
        this.certPath = null;
    }

    public static ClientSocket getSocketConnectionV2(Stage stage) throws IOException {
        // Create the custom dialog.
        Dialog<Pair<String, String>> dialog = new Dialog<>();
        dialog.setTitle("Server selector Dialog");
        dialog.setHeaderText("Please Insert the serverDetails here");

        // Set the button types.
        ButtonType loginButtonType = new ButtonType("Select Security Certificate", ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL);

        // Create the username and password labels and fields.
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField username = new TextField();
        username.setPromptText("Host");
        TextField password = new TextField();
        password.setPromptText("Port");

        grid.add(new Label("Host IP:"), 0, 0);
        grid.add(username, 1, 0);
        grid.add(new Label("Port number:"), 0, 1);
        grid.add(password, 1, 1);

        
        // Enable/Disable login button depending on whether a username was entered.
        Node loginButton = dialog.getDialogPane().lookupButton(loginButtonType);
        loginButton.setDisable(true);

        // Do some validation (using the Java 8 lambda syntax).
        username.textProperty().addListener((observable, oldValue, newValue) -> {
            
            loginButton.setDisable(newValue.trim().isEmpty());
        });

        dialog.getDialogPane().setContent(grid);

        // Request focus on the username field by default.
        Platform.runLater(() -> username.requestFocus());

        String certPathStr = null;

        // Convert the result to a username-password-pair when the login button is
        // clicked.
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == loginButtonType) {
                return new Pair<>(username.getText(), password.getText());
            }
            return null;
        });


        String hostName = "localhost";
        int portNumber = 6008;

        try {
            Optional<Pair<String, String>> result = dialog.showAndWait();
            if (result.isPresent()) {
                hostName = result.get().getKey();
                portNumber = Integer.parseInt(result.get().getValue());
            }

        certPathStr = getCertFile(stage);
        } catch (Exception e) {
            new Alert(AlertType.ERROR, "Error: no port number enterd; Port number is necessary in order to connect to the server\n" + e.getMessage()).showAndWait();
            //MultiInputDialog.getSocketConnectionV2(stage);
        }

        return new ClientSocket(hostName, portNumber, certPathStr);
    }

    private static String getCertFile(Stage stage) {
        String certPathStr = null;
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Text Files", "*.cer"));
        // fileChooser.setInitialDirectory(new
        // java.io.File(System.getProperty("user.home") + "/Desktop"));
        String str = fileChooser.showOpenDialog(stage).getAbsolutePath();
        try {
            Optional<String> certPath = Optional.of(str);
            System.out.println(certPath.get());
            if (certPath.isPresent()) {
                System.out.println("present");
                certPathStr = certPath.get();
            }
        } catch (Exception e) {
            new Alert(AlertType.ERROR, "Error: no certificate file selected; Certificate file is necessary in order to connect to the server\n" + e.getMessage()).showAndWait();
        }

        System.out.println("im here");
        System.out.println(certPathStr);
        return certPathStr;

    }

}
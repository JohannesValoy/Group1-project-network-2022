package no.ntnu.idata2304.group1.clientapp.app.ui;

import java.io.File;
import java.io.IOException;
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
import no.ntnu.idata2304.group1.clientapp.app.network.ClientSocket;

import static no.ntnu.idata2304.group1.clientapp.app.ui.ErrorDialogs.generalError;
import static no.ntnu.idata2304.group1.clientapp.app.ui.ErrorDialogs.invalidPortNumber;

/**
 * The type Multi input dialog.
 */
public class MultiInputDialog extends Dialog<ClientSocket> {

    /**
     * Instantiates a new Multi input dialog.
     */
    public MultiInputDialog() {
        this.setTitle("Server selector Dialog");
        this.setHeaderText("Please Insert the serverDetails here");

        // Set the button types.
        ButtonType loginButtonType =
                new ButtonType("Select Security Certificate", ButtonData.OK_DONE);
        this.getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL);

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
        Node loginButton = this.getDialogPane().lookupButton(loginButtonType);
        loginButton.setDisable(true);
        username.setOnAction(event -> password.requestFocus());

        // Do some validation (using the Java 8 lambda syntax).
        username.textProperty().addListener((observable, oldValue, newValue) -> {
            if(!password.getText().isEmpty() && !newValue.isEmpty()) {
                loginButton.setDisable(false);
            }
        });

        //Checks if both fields are filled and if so, enables the button
        password.textProperty().addListener((observable, oldValue, newValue) -> {
            if(!username.getText().isEmpty() && !newValue.isEmpty()) {
                loginButton.setDisable(false);
            }
        });

        this.getDialogPane().setContent(grid);

        // Request focus on the username field by default.
        Platform.runLater(username::requestFocus);

        // Convert the result to a username-password-pair when the login button is
        // clicked.
        this.setResultConverter(dialogButton -> {
            ClientSocket returnValue = null;
            if (dialogButton == loginButtonType) {
                String hostname = username.getText();
                Stage stage2 = new Stage();
                String cert = getCertFile(stage2);
                if (cert != null) {
                    try {
                        int port = Integer.parseInt(password.getText());
                        returnValue = new ClientSocket(hostname, port, cert);
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid port number");
                        invalidPortNumber(password.getText());
                        System.exit(0);
                    } catch (Exception e) {
                        generalError(e);
                        System.exit(0);
                    }

                }

            }
            return returnValue;
        });
    }

    private static String getCertFile(Stage stage) {
        String certPathStr = null;
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Certificate file ", "*.crt"),
                new FileChooser.ExtensionFilter("Certificate file ", "*.cer"),
                new FileChooser.ExtensionFilter("All Files", "*.*"));
        File certFile = fileChooser.showOpenDialog(stage);
        if (certFile == null) {
            new Alert(AlertType.ERROR,
                    "Error: no certificate file selected; Certificate file is necessary in order to connect to the server\n");
        } else {
            certPathStr = certFile.getAbsolutePath();
        }
        return certPathStr;

    }
}

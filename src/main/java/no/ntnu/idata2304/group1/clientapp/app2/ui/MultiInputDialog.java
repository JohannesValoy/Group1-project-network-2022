package no.ntnu.idata2304.group1.clientapp.app2.ui;

import java.io.IOException;
import java.util.Optional;
import javafx.scene.control.Dialog;
import javafx.scene.control.TextInputDialog;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Pair;
import no.ntnu.idata2304.group1.clientapp.app2.network.ClientSocket;

public class MultiInputDialog{

    static Dialog<Pair<String, String>> dialog;
    
    public MultiInputDialog() {}

    public static ClientSocket getSocketConnection(Stage stage) throws IOException {
        String hostName = "localhost";
        int portNumber = 6008;
        String certPathStr = null;

        TextInputDialog textDialogHostName = new TextInputDialog("Host IP (Ex: 10.24.90.163)");
        textDialogHostName.setTitle("Host Name");
        textDialogHostName.setHeaderText(
                "Please enter the host name of the server you want to connect to");
        textDialogHostName.setContentText("Please enter the host name:");
        Optional<String> result = textDialogHostName.showAndWait();
        if (result.isPresent()) {
            if(result.get().contains(".")){
                hostName = result.get();
            } else {
                throw new IOException("Invalid host name, must be an IP address in this format: 10.24.90.163");
            }
    
        }
        TextInputDialog textDialogPortNumber = new TextInputDialog("Port Number (Ex: 8080)");
            textDialogPortNumber.setTitle("Port Number");
            textDialogPortNumber
                    .setHeaderText("Please enter the port of the server you want to connect to");
            textDialogPortNumber.setContentText("Please enter the port:");
            Optional<String> portNumberResult = textDialogPortNumber.showAndWait();
            if (portNumberResult.isPresent()) {
                if(portNumberResult.get().matches("[0-9999]+")){
                    portNumber = Integer.parseInt(portNumberResult.get());
                } else {
                    throw new IOException("Invalid host name, host port must be a four digit number");
                }
            }

            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Open Resource File");
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("Text Files", "*.cer"));
            fileChooser.setInitialDirectory(new java.io.File("C:\\Users\\johan\\Desktop\\Group1testfolder\\src\\test\\resources\\no\\ntnu\\idata2304\\group1\\clientapp\\app2\\network\\trustedCerts"));
            fileChooser.showOpenDialog(stage);
            Optional<String> certPath = Optional.of(fileChooser.getInitialDirectory().getAbsolutePath());
            if (certPath.isPresent()) {
                    certPathStr = certPath.get();
            }
        return new ClientSocket(hostName, portNumber, certPathStr); 
}
}
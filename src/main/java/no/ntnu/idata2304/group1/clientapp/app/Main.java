package no.ntnu.idata2304.group1.clientapp.app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import no.ntnu.idata2304.group1.clientapp.app.ui.MainController;

public class Main extends Application {

    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
    public static void main(String[] args) {
        Application.launch(args);
    }


    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent mainWindow = FXMLLoader.load(MainController.class.getResource("MainScene.fxml"));
        Scene scene = new Scene(mainWindow);
        primaryStage.setTitle("Room Manager");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

}

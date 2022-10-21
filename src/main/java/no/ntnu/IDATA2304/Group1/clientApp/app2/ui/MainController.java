package no.ntnu.IDATA2304.Group1.clientApp.app2.ui;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import no.ntnu.IDATA2304.Group1.clientApp.app2.logic.Room;
import no.ntnu.IDATA2304.Group1.clientApp.app2.logic.Sensor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class MainController extends Application {
    ArrayList<RoomWindowController> roomWindowControllers;



    Button button;
    @Override
    public void start(Stage stage) throws IOException {
        roomWindowControllers = new ArrayList<>();
        //TODO: remove exampleCode
        int n = 0;
        while(n <= 100) {
            RoomWindowController currentRoomWindowController = new RoomWindowController();
            currentRoomWindowController.setRoom(new Room(n, "Room " + n));
            roomWindowControllers.add(currentRoomWindowController);
            n++;
            currentRoomWindowController.getRoom().getRoomNumber();
        }

        FXMLLoader mainWindowLoader = new FXMLLoader(MainController.class.getResource("MainScene.fxml"));
        Stage mainStage = makeStage(mainWindowLoader, stage, 1000, 700);
        mainStage.setTitle("Rooms");
        if(roomWindowControllers.size() > 10000){
            throw new IllegalArgumentException("Amount of rooms are limited to 10000");
        }
        int i = 0;
        while (i < roomWindowControllers.size()){
            MainWindowController mainWindowController = (MainWindowController) mainWindowLoader.getController();
            String roomName = "Room " + (i);
            Button button = new Button(roomName);
            button.prefHeight(300);
            button.prefWidth(400);
            int finalI = i;
            button.setOnMouseClicked(mouseEvent -> {
                try {
                    loadRoomView(finalI, roomName);
                } catch (IOException e){
                throw new IllegalArgumentException("Could not load rooms");
                }
            });
            mainWindowController.getFlowPane().getChildren().add(i, button);
            i++;
        }

    }

    @FXML
    private void loadRoomView(int roomNumber, String roomName) throws IOException {
        RoomWindowController roomWindowController = roomWindowControllers.get(roomNumber);
        FXMLLoader roomWindowLoader = new FXMLLoader(MainController.class.getResource("RoomScene.fxml"));
        Stage roomStage = makeStage(roomWindowLoader, new Stage(), 500, 350);
        RoomWindowController roomWindowController1 = (RoomWindowController) roomWindowLoader.getController();
        roomWindowController1.setRoom(roomWindowController.getRoom());
        roomStage.setTitle(roomName);

    }

    public Stage makeStage(FXMLLoader fxmlLoader, Stage stage, int width, int height) throws IOException {
        Scene scene = new Scene(fxmlLoader.load(), width, height);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
        return stage;
    }

    public static void main(String[] args) {
        launch();
    }
}
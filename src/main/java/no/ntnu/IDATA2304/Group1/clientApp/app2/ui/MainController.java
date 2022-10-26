package no.ntnu.IDATA2304.Group1.clientApp.app2.ui;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.SubScene;
import javafx.scene.chart.LineChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import no.ntnu.IDATA2304.Group1.clientApp.app2.logic.Room;

import java.io.IOException;
import java.util.ArrayList;

public class MainController extends Application {
    ArrayList<RoomWindowController> roomWindowControllers;



    Button button;
    @Override
    public void start(Stage stage) throws IOException {
        roomWindowControllers = new ArrayList<>();
        // Loads example rooms to the main window
        //TODO: remove exampleCode
        int n = 0;
        while(n <= 100) {
            RoomWindowController currentRoomWindowController = new RoomWindowController();
            currentRoomWindowController.setRoom(new Room(n, "Room " + n));
            roomWindowControllers.add(currentRoomWindowController);
            n++;
            currentRoomWindowController.getRoom().getRoomNumber();
        }

        // Loads the main window
        FXMLLoader mainWindowLoader = new FXMLLoader(MainController.class.getResource("MainScene.fxml"));
        Stage mainStage = makeStage(mainWindowLoader, stage, 1000, 700);
        mainStage.setTitle("Rooms");
        if(roomWindowControllers.size() > 10000){
            throw new IllegalArgumentException("Amount of rooms are limited to 10000");
        }

        MainWindowController mainWindowController = (MainWindowController) mainWindowLoader.getController();
        loadRoomsIntoMainWindow(mainWindowController, stage);
    }

    public void loadRoomsIntoMainWindow(MainWindowController mainWindowController, Stage stage) throws IOException {
        int i = 0;
        while (i < roomWindowControllers.size()){
            String roomName = "Room " + (i);
            Pane pane = new Pane();
            pane.getChildren().add(loadRoomView(i, roomName).load());
            pane.setStyle(
                    "-fx-background-color: grey;" +
                            "-fx-background-radius: 30;" +
                            "-fx-border-color: none;"
            );
            pane.setPrefSize(420, 420);
            int finalI = i;
            pane.setOnMouseClicked(mouseEvent -> {
                if (mouseEvent.getClickCount() == 2) {
                    contractPane(mainWindowController);
                    pane.setPrefSize(mainWindowController.scrollPane.getWidth() -140, mainWindowController.scrollPane.getHeight() - 140);
                    System.out.println("Clicked on " + roomName);
                }

            });
            mainWindowController.scrollPane.prefWidthProperty().bind(stage.widthProperty());
            mainWindowController.scrollPane.prefHeightProperty().bind(stage.heightProperty());
            mainWindowController.flowPane.prefWidthProperty().bind(mainWindowController.scrollPane.widthProperty());
            mainWindowController.flowPane.prefHeightProperty().bind(mainWindowController.scrollPane.heightProperty());
            mainWindowController.getFlowPane().getChildren().add(i, pane);
            i++;
        }
        mainWindowController.getFlowPane().setOnMouseClicked(mouseEvent -> {
            if (mouseEvent.getClickCount() == 2) {
                System.out.println("Double clicked");
            } else {
                contractPane(mainWindowController);
                System.out.println("Clicked");
            }
        });
    }

    private void contractPane(MainWindowController mainWindowController) {
        int count = 0;
        while (count < roomWindowControllers.size()){
            Pane currentPane = (Pane) mainWindowController.getFlowPane().getChildren().get(count);
            currentPane.setPrefSize(420, 420);
            count++;
        }
    }

    /** Loads room windows
     *
     * @param roomNumber The number of the room to be loaded
     * @param roomName The room name, also used for the title of the window
     * @throws IOException If the room window could not be loaded
     */
    @FXML
    private FXMLLoader loadRoomView(int roomNumber, String roomName){
        RoomWindowController roomWindowController = roomWindowControllers.get(roomNumber);
        FXMLLoader roomWindowLoader = new FXMLLoader(MainController.class.getResource("RoomScene.fxml"));
        RoomWindowController roomWindowController1 = (RoomWindowController) roomWindowLoader.getController();
        return roomWindowLoader;

    }

    /** Creates a stage from a loader, stage, width and height.
     *
     * @param fxmlLoader The loader to load the stage from.
     * @param stage The stage to load the loader into.
     * @param width The width of the stage.
     * @param height The height of the stage.
     * @return The stage.
     *
     * @throws IOException If the loader fails to load the stage.
     */

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
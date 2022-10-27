package no.ntnu.idata2304.group1.clientApp.app2.ui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import no.ntnu.idata2304.group1.clientApp.app2.logic.Room;
import no.ntnu.idata2304.group1.clientApp.app2.ui.MainWindowController;
import no.ntnu.idata2304.group1.clientApp.app2.ui.RoomWindowController;
import no.ntnu.idata2304.group1.clientApp.app2.logic.Sensor;

import java.io.IOException;
import java.util.ArrayList;

public class MainController extends Application {
    ArrayList<RoomWindowController> roomWindowControllers;


    @Override
    public void start(Stage stage) throws IOException {
        roomWindowControllers = new ArrayList<>();
        addExampleRooms();
        loadMainWindow(stage);
        addExampleSensors();
    }


    /**
     * Loads the main window and sets the stage
     * @param stage the stage to set
     * @throws IOException if the fxml file is not found
     */
    public void loadMainWindow(Stage stage) throws IOException {
        FXMLLoader mainWindowLoader = new FXMLLoader(MainController.class.getResource("MainScene.fxml"));
        Stage mainStage = makeStage(mainWindowLoader, stage, 1000, 700);
        mainStage.setTitle("Rooms");
        if(roomWindowControllers.size() > 10000){
            throw new IllegalArgumentException("Amount of rooms are limited to 10000");
        }
        MainWindowController mainWindowController = (MainWindowController) mainWindowLoader.getController();
        loadRoomsIntoMainWindow(mainWindowController, stage);
    }

    /**
     * Adds room to room list
     * @param roomNumber the number of the room to be added
     */
    public void addRoom(int roomNumber){
        RoomWindowController currentRoomWindowController = new RoomWindowController();
        currentRoomWindowController.setRoom(new Room(roomNumber, "Room " + roomNumber));
        if(roomWindowControllers.size() <= roomNumber){
            roomWindowControllers.add(roomNumber, currentRoomWindowController);
        } else {
            System.out.println(roomNumber + " " + roomWindowControllers.size());
            roomWindowControllers.set(roomNumber, currentRoomWindowController);
        }
    }

    /**
     * Adds 10 example rooms to the roomWindowControllers
     */
    public void addExampleRooms(){
        int n = 0;
        while(n <= 5) {
            addRoom(n);
            n++;
        }
    }

    public void addExampleSensors(){
        ArrayList<Sensor> sensorList = new ArrayList<>();
        for(int i = 0; i < roomWindowControllers.size(); i++) {
            for(int count = 0; count < 3; count++) {
                Sensor sensor = new Sensor("Rain in mm", i);
                sensorList.add(sensor);
            }
            roomWindowControllers.get(i).getRoom().setSensorList(sensorList);
            System.out.println(i + " sensorlist size: " + roomWindowControllers.get(i).getRoom().getListOfSensors().size() + " roomwindowcontrollers.size: " + roomWindowControllers.size());
        }

    }

    /**
     * Loads the rooms into the main window
     * @param mainWindowController the main window controller;
     * @param stage the main stage
     * @throws IOException if the fxml file could not be loaded;
     */
    public void loadRoomsIntoMainWindow(MainWindowController mainWindowController, Stage stage) throws IOException {
        int i = 0;
        while (i < roomWindowControllers.size()){
            String roomName = "Room " + (i);
            Pane pane = new Pane();

            FXMLLoader roomWindowLoader = new FXMLLoader(MainController.class.getResource("RoomScene.fxml"));
            pane.getChildren().add(roomWindowLoader.load());
            RoomWindowController roomWindowController = (RoomWindowController) roomWindowLoader.getController();
            roomWindowController.setRoom(new Room(i, roomName));
            roomWindowController.setTitle(roomName);
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
            if (!(mouseEvent.getClickCount() == 2)) {
                contractPane(mainWindowController);
            }
        });
    }

    /**
     * Contracts the pane to the original size
     * @param mainWindowController the main window controller;
     */
    private void contractPane(MainWindowController mainWindowController) {
        int count = 0;
        while (count < roomWindowControllers.size()){
            Pane currentPane = (Pane) mainWindowController.getFlowPane().getChildren().get(count);
            currentPane.setPrefSize(420, 420);
            count++;
        }
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

    /**
     * Main method
     * @param args
     */
    public static void main(String[] args) {
        launch();
    }
}
package no.ntnu.idata2304.group1.clientapp.app.ui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;
import no.ntnu.idata2304.group1.clientapp.app.network.ClientSocket;
import no.ntnu.idata2304.group1.data.Room;


/**
 * Main controller for the UI. 
 * Creates and loads the main window and loads the room windows into the main window.
 */
public class MainController extends Application {
    private MainWindowController mainWindowController;
    private ArrayList<RoomWindowController> roomWindowControllers;
    @FXML
    private FlowPane flowPane;

    private ArrayList<String> rooms;
    private ClientSocket clientSocket;
    private ArrayList<Room> clientRooms;



    /**
     * starts the application.
     *
     * @param stage the stage
     */
    public void start(Stage stage) {
        this.roomWindowControllers = new ArrayList<>();
        this.rooms = new ArrayList<>();
        this.clientRooms = new ArrayList<>();

        try {
            this.clientSocket = MultiInputDialog.getSocketConnectionV2(stage);
            this.clientRooms = this.clientSocket.getRoomData(rooms);
        } catch (IOException e) {
            ErrorDialogs.couldNotConnectAlert(e);
        } catch (ClassNotFoundException e) {
            ErrorDialogs.couldNotUpdateRoom(e);
        } catch (NullPointerException e) {
            System.exit(0);
        }

        autoUpdateRoomData();

        // Loads mainScene
        FXMLLoader mainWindowLoader =
                new FXMLLoader(MainController.class.getResource("MainScene.fxml"));
        try {
            Stage mainStage = makeStage(mainWindowLoader, stage, 1000, 700);
            mainStage.setTitle("Rooms");
        } catch (IOException e) {
            new Alert(AlertType.ERROR, "Could not load main window").showAndWait();
        }

        mainWindowController = mainWindowLoader.getController();

        // Retrieves flowPane
        this.flowPane = this.mainWindowController.getFlowPane();

        // Binds the flowPane to the stage dimensions
        this.flowPane.prefWidthProperty().bind(stage.widthProperty());
        this.flowPane.prefHeightProperty().bind(stage.heightProperty());

        for(Room room : clientRooms) {            
            try {
                addRoom(room, stage);
            } catch (IOException e) {
                new Alert(AlertType.ERROR, e.getMessage() + " - Could not add room").showAndWait();
            }
        }
    }

    public void autoUpdateRoomData(){
            Timer timer = new Timer();
            timer.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    Platform.runLater(() -> {
                        updateRoomData();
                    });
                }
            }, 1, 5000);
    }

    public void updateRoomData(){
        try {
            for(Room clientRoom : clientSocket.getRoomData(rooms)){
                for(RoomWindowController roomWindowController : roomWindowControllers){
                    if(roomWindowController.getRoom().getName().equals(clientRoom.getName())){
                        roomWindowController.setRoom(clientRoom);
                        roomWindowController.update();
                    }
                }
            }
        } catch (IOException e) {
            ErrorDialogs.couldNotConnectAlert(e);

        } catch (ClassNotFoundException e) {
           ErrorDialogs.couldNotUpdateRoom(e);
        }
    }



    /**
     * Loads the rooms into the main window
     * abcd
     * @param stage the main stage
     * @throws IOException if the fxml file could not be loaded;
     */
    public void addRoom(Room room, Stage stage) throws IOException {
        FXMLLoader roomWindowLoader =
                new FXMLLoader(MainController.class.getResource("RoomScene.fxml"));
        this.mainWindowController.getFlowPane().getChildren().add(roomWindowLoader.load());
        RoomWindowController roomWindowController = roomWindowLoader.getController();
        roomWindowControllers.add(roomWindowController);
        roomWindowController.setRoom(room);
        // Sets the pane size and style



        // Sets the on click event for the room
        roomWindowController.getPane().setOnMouseClicked(mouseEvent -> {
            if (mouseEvent.getClickCount() == 2)
                roomWindowController.expandRoomView(stage.getWidth(), stage.getHeight());
        });

        // Contracts the roomWindow if the room is clicked
        mainWindowController.getFlowPane().setOnMouseClicked(mouseEvent -> {
            if (mouseEvent.getClickCount() != 2) {
                contractPane();
            }
        });
    }

    /**
     * Contracts the pane to the original size
     */
    private void contractPane() {
        for (RoomWindowController roomWindowController : roomWindowControllers)
            roomWindowController.contractRoomView();
    }


    /**
     * Creates a stage from a loader, stage, width and height.
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
     */
    public static void main(String[] args) {
        launch();
    }
}

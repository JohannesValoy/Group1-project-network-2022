package no.ntnu.idata2304.group1.clientapp.app2.ui;

import java.io.IOException;
import java.util.ArrayList;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;
import no.ntnu.idata2304.group1.clientapp.app2.network.ClientSocket;
import no.ntnu.idata2304.group1.data.Room;
import no.ntnu.idata2304.group1.data.Sensor;

/**
 * TODO: intergrate better with ClienSocket
 * ---------------------------------------------------------------------- Main controller for the
 * UI. Creates and loads the main window and loads the room windows into the main window.
 */
public class MainController extends Application {
    private MainWindowController mainWindowController;
    private ArrayList<RoomWindowController> roomWindowControllers;
    private FlowPane flowPane;


    /**
     * starts the application.
     *
     * @param stage the stage
     *
     * @throws IOException if the fxml file could not be loaded
     */
    public void start(Stage stage) {
        ClientSocket clientSocket;
        this.roomWindowControllers = new ArrayList<>();
        try {
            clientSocket = new ClientSocket("10.24.90.163", 6008,
                    "C:\\Users\\johan\\Desktop\\Group1testfolder\\src\\test\\resources\\no\\ntnu\\idata2304\\group1\\clientapp\\app2\\trustedCerts");
            ArrayList<Room> rooms = clientSocket.getListOfRooms();
            if (rooms != null) {
                for (int i = 0; i < rooms.size(); i++) {
                    addRoom(clientSocket.getListOfRooms().get(i), stage);
                }
            }
        } catch (IOException e) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Could not connect to server");
            alert.setContentText("Please check if the server is running");
            alert.showAndWait();
            System.exit(0);
        }

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

        // Initiates flowPane
        this.flowPane = this.mainWindowController.flowPane;

        // Binds the flowPane to the stage dimensions
        this.flowPane.prefWidthProperty().bind(stage.widthProperty());
        this.flowPane.prefHeightProperty().bind(stage.heightProperty());
        // Example rooms.
        try {
            addExampleRooms(stage);
        } catch (IOException e) {
            new Alert(Alert.AlertType.WARNING, e.getMessage());
        }
    }


    /**
     * Loads the rooms into the main window
     * 
     * @param stage the main stage
     * @throws IOException if the fxml file could not be loaded;
     */
    public void addRoom(Room room, Stage stage) throws IOException {
        FXMLLoader roomWindowLoader =
                new FXMLLoader(MainController.class.getResource("RoomScene.fxml"));
        flowPane.getChildren().add(roomWindowLoader.load());
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
    public Stage makeStage(FXMLLoader fxmlLoader, Stage stage, int width, int height)
            throws IOException {
        Scene scene = new Scene(fxmlLoader.load(), width, height);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
        return stage;
    }

    /**
     * TODO: Remove example methods Adds 10 example rooms to the roomWindowControllers
     */
    public void addExampleRooms(Stage stage) throws IOException {
        int n = 0;
        while (n <= 2) {
            Room room = new Room(n, "Room From AddExampleRooms" + n);
            addRoom(room, stage);
            addExampleSensorsLive(n);
            n++;
        }
    }

    public void addExampleSensorsLive(int number) {
        ArrayList<Sensor> sensorList = new ArrayList<>();
        for (int count = 0; count < 3; count++) {
            Sensor sensor = new Sensor(Sensor.Types.TEMPERATURE, number + "");
            sensorList.add(sensor);
        }
        roomWindowControllers.get(number).getRoom().setSensorList(sensorList);
    }

    /**
     * Main method
     */
    public static void main(String[] args) {
        launch();
    }
}

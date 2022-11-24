package no.ntnu.idata2304.group1.clientapp.app2.ui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Timer;
import java.util.TimerTask;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.FlowPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import no.ntnu.idata2304.group1.clientapp.app2.network.ClientSocket;
import no.ntnu.idata2304.group1.data.Room;

/**
 * TODO: intergrate better with ClienSocket
 * ---------------------------------------------------------------------- Main controller for the
 * UI. Creates and loads the main window and loads the room windows into the main window.
 */
public class MainController extends Application {
    private MainWindowController mainWindowController;
    private ArrayList<RoomWindowController> roomWindowControllers;
    private FlowPane flowPane;
    private ArrayList<String> rooms;
    private ClientSocket clientSocket;
    private ArrayList<Room> clientRooms;



    /**
     * starts the application.
     *
     * @param stage the stage
     *
     * @throws IOException if the fxml file could not be loaded
     */
    public void start(Stage stage) {
        this.roomWindowControllers = new ArrayList<>();
        this.rooms = new ArrayList<>();
        this.clientRooms = new ArrayList<>();

        for (RoomWindowController roomWindowController : roomWindowControllers) {
            rooms.add(roomWindowController.getRoom().getName());
        }

        try {
            this.clientSocket = getServerConnection(stage);
            this.clientRooms = this.clientSocket.getRoomData(rooms);
        } catch (IOException e) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Could not connect to server");
            alert.setContentText(e.getMessage()+ " - Make sure the server is running and all the details entered are correct");
            alert.showAndWait();
            System.exit(0);
        } catch (ClassNotFoundException e) {
            new Alert(AlertType.ERROR, e.getMessage());
        }

        updateRoomData();

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
        this.flowPane = this.mainWindowController.getFlowPane();

        // Binds the flowPane to the stage dimensions
        this.flowPane.prefWidthProperty().bind(stage.widthProperty());
        this.flowPane.prefHeightProperty().bind(stage.heightProperty());

        for(Room room : clientRooms) {
            System.out.println("Loading room: " + room.getName());
            
            try {
                addRoom(room, stage);
            } catch (IOException e) {
                // TODO Auto-generated catch block
                new Alert(AlertType.ERROR, e.getMessage());
                e.printStackTrace();
            }
        }
    }

    public void updateRoomData(){
            Timer timer = new Timer();
            timer.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    Platform.runLater(() -> {
                            try {
                                for(Room clientRoom : clientSocket.getRoomData(rooms)){
                                    for(RoomWindowController roomWindowController : roomWindowControllers){
                                        if(roomWindowController.getRoom().getName().equals(clientRoom.getName())){
                                            roomWindowController.update();
                                            roomWindowController.setRoom(clientRoom);
                                        }
                                    }
                                }
                            } catch (IOException e) {
                                Alert alert = new Alert(AlertType.ERROR);
                                alert.setTitle("Error");
                                alert.setHeaderText("Could not connect to server");
                                alert.setContentText(e.getMessage());
                                alert.showAndWait();
                                System.exit(0);
                            } catch (ClassNotFoundException e) {
                                new Alert(AlertType.ERROR, e.getMessage());
                            }
                    });
                }
            }, 1, 5000);
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
    public Stage makeStage(FXMLLoader fxmlLoader, Stage stage, int width, int height)
            throws IOException {
        Scene scene = new Scene(fxmlLoader.load(), width, height);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
        return stage;
    }


    public ClientSocket getServerConnection(Stage stage) throws IOException {
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
                //TODO: Check if file is a .cer file
                    certPathStr = certPath.get();
            }
        return new ClientSocket(hostName, portNumber, certPathStr); 
    }

    /**
     * Main method
     */
    public static void main(String[] args) {
        launch();
    }
}

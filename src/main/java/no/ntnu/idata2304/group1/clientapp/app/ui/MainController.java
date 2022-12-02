package no.ntnu.idata2304.group1.clientapp.app.ui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Timer;
import java.util.TimerTask;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import no.ntnu.idata2304.group1.clientapp.app.network.ClientSocket;
import no.ntnu.idata2304.group1.data.Room;


/**
 * Main controller for the UI. Creates and loads the main window and loads the room windows into the
 * main window.
 */
public class MainController {
    private ArrayList<RoomWindowController> roomWindowControllers;
    private final MultiInputDialog multiInputDialog = new MultiInputDialog();
    @FXML
    private FlowPane flowPane;
    @FXML
    private BorderPane borderPane;
    @FXML
    private ScrollPane scrollPane;
    private ArrayList<String> rooms;
    private ClientSocket clientSocket;
    private ArrayList<Room> clientRooms;


    /**
     * starts the application.
     */
    @FXML
    public void initialize() {
        this.roomWindowControllers = new ArrayList<>();
        this.rooms = new ArrayList<>();
        this.clientRooms = new ArrayList<>();
        while(this.clientSocket == null) {
            try {
                System.out.println("Connecting to server...");
                this.clientSocket = multiInputDialog.showAndWait().get();
                this.clientRooms = this.clientSocket.getRoomData(rooms);
                System.out.println("Connected to server");
            } catch (IOException e) {
                ErrorDialogs.couldNotConnectAlert(e);
            } catch (ClassNotFoundException e) {
                ErrorDialogs.couldNotUpdateRoom(e);
            } catch (NullPointerException | NoSuchElementException e) {
                System.exit(0);
            } catch (RuntimeException e) {
                ErrorDialogs.invalidPortNumber(e.getMessage());
            } catch (Exception e) {
                ErrorDialogs.generalError(e);
            }
        }

        autoUpdateRoomData();

        // Binds the flowPane to the stage dimensions

        this.flowPane.prefWidthProperty().bind(borderPane.widthProperty());
        this.flowPane.prefHeightProperty().bind(borderPane.heightProperty());

        for (Room room : clientRooms) {
            try {
                addRoom(room);
            } catch (IOException e) {
                new Alert(AlertType.ERROR, e.getMessage() + " - Could not add room").showAndWait();
            }
        }
    }

    /**
     * Auto update room data.
     */
    public void autoUpdateRoomData() {
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(MainController.this::updateRoomData);
            }
        }, 1, 5000);
    }

    /**
     * Update room data.
     */
    public void updateRoomData() {
        try {
            for (Room clientRoom : clientSocket.getRoomData(rooms)) {
                for (RoomWindowController roomWindowController : roomWindowControllers) {
                    if (roomWindowController.getRoom().getName().equals(clientRoom.getName())) {
                        roomWindowController.setRoom(clientRoom);
                        roomWindowController.update();
                    }
                }
                this.scrollPane.setFitToWidth(true);
                this.scrollPane.setFitToHeight(true);
            }
        } catch (IOException e) {
            ErrorDialogs.couldNotConnectAlert(e);

        } catch (ClassNotFoundException e) {
            ErrorDialogs.couldNotUpdateRoom(e);
        }
    }


    /**
     * Loads the rooms into the main window
     *
     * @param room the room
     * @throws IOException if the fxml file could not be loaded;
     */
    public void addRoom(Room room) throws IOException {
        FXMLLoader roomWindowLoader =
                new FXMLLoader(MainController.class.getResource("RoomScene.fxml"));
        this.flowPane.getChildren().add(roomWindowLoader.load());

        RoomWindowController roomWindowController = roomWindowLoader.getController();
        roomWindowControllers.add(roomWindowController);
        roomWindowController.setRoom(room);
        // Sets the pane size and style



        // Sets the on click event for the room
        roomWindowController.getPane().setOnMouseClicked(mouseEvent -> {
            if (mouseEvent.getClickCount() == 2)
                roomWindowController.expandRoomView(borderPane.getWidth(), borderPane.getHeight());
        });

        // Contracts the roomWindow if the room is clicked
        this.flowPane.setOnMouseClicked(mouseEvent -> {
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

}

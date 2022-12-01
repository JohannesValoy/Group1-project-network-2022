package no.ntnu.idata2304.group1.clientapp.app2.ui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import no.ntnu.idata2304.group1.data.Room;
import no.ntnu.idata2304.group1.data.SensorRecord;
import no.ntnu.idata2304.group1.data.SortbyDate;

import java.util.Collections;
import java.util.List;

/**
 * The type Room window controller.
 */
public class RoomWindowController {
    /**
     * The Title.
     */
    @FXML
    private Label title;
    /**
     * The Pane.
     */
    @FXML
    private FlowPane pane;
    /**
     * The H box.
     */
    @FXML
    private FlowPane HBox;
    /**
     * The Current temperature.
     */
    @FXML
    private Label CurrentTemperature;
    @FXML
    private LineChart<String, Number> sensorChart;
    private Room room;

    /**
     * The Auto update.
     */
    boolean autoUpdate = true;


    /**
     * Instantiates a new Room window controller.
     */
    public RoomWindowController() {
    }

    /**
     * Sets room.
     *
     * @param room the room
     */
    public void setRoom(Room room) {
        this.room = room;
        
        pane.setPrefSize(420, 420);
        pane.setStyle("-fx-background-color: grey;" + "-fx-border-radius: 150px;");
        update();
    }

    public void update(){
        updateChart(sensorChart, 0);
    }

    /**
     * Expand room view.
     *
     * @param height the height
     * @param width the width
     */
    public void expandRoomView(double height, double width) {
        sensorChart.setPrefSize(width * 10 / 21, height / 2);
        HBox.setPrefWidth(width);
        for (int i = 1; i < room.getListOfSensors().size(); i++) {
            LineChart<String, Number> sensorChart2 =
                    new LineChart<>(new CategoryAxis(), new NumberAxis()) {};
            HBox.getChildren().add(sensorChart2);
            sensorChart2.setPrefSize(width * 10 / 21, height / 2);
            updateChart(sensorChart2, i);
        }
    }

    /**
     * Contract room view.
     */
    public void contractRoomView() {
        sensorChart.setPrefSize(400, 350);
        pane.setPrefSize(420, 420);
        HBox.setPrefSize(420, 420);
        for (int i = 1; i < HBox.getChildren().size(); i++) {
            try {
                HBox.getChildren().remove(i);
            } catch (Exception e) {
                System.out.println("Error removing sensor chart");
                //TODO: handle exception; This is a hacky fix for a bug that I don't know how to fix;
            }
        }
    }

    /**
     * Update sensor chart.
     *
     * @param sensorChart the sensor chart
     * @param sensorID the sensor id
     */
    @FXML
    public void updateSensorChart(LineChart sensorChart, int sensorID) {
        if (this.room != null) {
            if (sensorChart != null) {
                sensorChart.getData().clear();
                try {
                    sensorChart.getData().addAll(getChartData(sensorID));
                } catch (Exception e) {
                    autoUpdate = false;
                    new Alert(Alert.AlertType.ERROR, e.getMessage() + " error updating the sensor chart").showAndWait();
                }
            } else {
                new Alert(Alert.AlertType.ERROR,
                        "Line Chart Failed to Load in room: " + room.getName() + " try again or wait for and update to the software").showAndWait();
            }
        } else {
            new Alert(Alert.AlertType.ERROR, "Room does not exist").showAndWait();
        }

    }

    /**
     * Get room the room.
     *
     * @return the room
     */
    public Room getRoom() {
        return this.room;
    }

    /**
     * Updates observable for line chart
     * 
     * @param sensorID the id of the sensor to get the data from
     * @return returns observable list
     */
    private ObservableList<XYChart.Series<String, Double>> getChartData(int sensorID) {

        XYChart.Series<String, Double> series = new XYChart.Series<>();
        if (this.room.getListOfSensors().size() <= sensorID) {
            throw new IllegalArgumentException("Requested sensor " + sensorID + " but room "
                    + room.getName() + " has " + this.room.getListOfSensors().size() + " sensors");
        }
        series.setName(this.room.getListOfSensors().get(sensorID).getTypeName());
        this.title.setText("Room Number " + this.room.getName());
        List<SensorRecord> sensorReadings = this.room.getListOfSensors().get(sensorID).getHistoryLog();
              sensorReadings.sort(new SortbyDate());
        for (SensorRecord sensorReading : sensorReadings) {
            
            series.getData()
                    .add(new XYChart.Data<>(
                            convertIntToTwoDigits(sensorReading.date().getHour()) + "."
                                    + convertIntToTwoDigits(sensorReading.date().getMinute()) + "." + convertIntToTwoDigits(sensorReading.date().getSecond()),
                            sensorReading.value()));

        }
        ObservableList<XYChart.Series<String, Double>> seriesList =
                FXCollections.observableArrayList();
        seriesList.addAll(series);

        return seriesList;
    }

    public int convertIntToTwoDigits(int number) {
        if (number < 10) {
            return Integer.parseInt("0" + number);
        }
        return number;
    }

    /**
     * Get pane pane.
     *
     * @return the pane
     */
    public Pane getPane() {
        return this.pane;
    }

    /**
     * Updates the chart every second
     */
    private void updateChart(LineChart<String, Number> sensorChart, int sensorID) {
        updateStandardSensorChart(sensorChart, sensorID);
    }

    /**
     * Update standard sensor chart.
     *
     * @param sensorChart the sensor chart
     * @param sensorID the sensor id
     */
    public void updateStandardSensorChart(LineChart<String, Number> sensorChart, int sensorID) {
        updateSensorChart(sensorChart, sensorID);
    }
}

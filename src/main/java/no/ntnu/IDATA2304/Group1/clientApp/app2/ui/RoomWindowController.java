package no.ntnu.idata2304.group1.clientapp.app2.ui;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import no.ntnu.idata2304.group1.clientapp.app2.logic.Room;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class RoomWindowController {
    public Label title;
    @FXML
    public LineChart sensorChart;
    private Room room;
    private int roomNumber;

    public RoomWindowController(){
        autoUpdateChart();
    }

    public void setRoom(Room room) {
        this.room = room;
        this.roomNumber = room.getRoomNumber();
    }

    @FXML
    public void updateSensorChart() {
        if(this.room != null){
            if(this.sensorChart != null) {
                this.sensorChart.getData().clear();
                try {
                    this.sensorChart.getData().addAll(getChartData());
                } catch (Exception e) {
                    new Alert(Alert.AlertType.ERROR, e.getMessage()).showAndWait();
                }
            } else {
                new Alert(Alert.AlertType.ERROR, "Line Chart Failed to Load").showAndWait();
            }
        } else {
            new Alert(Alert.AlertType.ERROR, "Room " + roomNumber + " Failed to load").showAndWait();
        }

    }

    public void setTitle(String title){
        this.title.setText(title);
    }
    public Room getRoom(){
        return this.room;
    }

    private ObservableList<XYChart.Series<Integer, Integer>> getChartData() throws NullPointerException{

        XYChart.Series<Integer, Integer> series = new XYChart.Series<>();
        if(this.room.getListOfSensors().isEmpty()){
            throw new NullPointerException("Room " + roomNumber + " has no sensors");
        }
        series.setName(this.room.getListOfSensors().get(0).getType());
        this.title.setText("Room Number " + this.roomNumber);
        List<Integer> sensorReadings = this.room.getListOfSensors().get(0).getHistoryLog();
        for (int i = 0; i < sensorReadings.size(); i++) {
            series.getData().add(new XYChart.Data(i, sensorReadings.get(i)));
        }
        ObservableList<XYChart.Series<Integer, Integer>> seriesList = FXCollections.observableArrayList();
        seriesList.addAll(series);

        return seriesList;
    }

    /**
     * Updates the chart every second
     */
    private void autoUpdateChart() {
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> {
                    updateSensorChart();
                });
            }
        }, 1, 2000);
    }

    public LineChart getSensorChart(){
        return this.sensorChart;
    }
}

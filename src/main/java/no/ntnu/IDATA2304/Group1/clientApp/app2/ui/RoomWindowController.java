package no.ntnu.idata2304.group1.clientapp.app2.ui;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.Axis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import no.ntnu.idata2304.group1.clientapp.app2.logic.Room;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class RoomWindowController {
    public Label title;
    public BorderPane borderPane;
    public FlowPane HBox;
    @FXML
    private LineChart sensorChart;
    private Room room;
    private int roomNumber;

    boolean autoUpdate = true;

    public RoomWindowController(){
        autoUpdateChart();
    }

    public void setRoom(Room room) {
        this.room = room;
        this.roomNumber = room.getRoomNumber();
    }

    public void expandRoomView(double height, double width){
        sensorChart.setPrefSize(width*5/11, height-400);
        LineChart sensorChart2 = new LineChart<>(new NumberAxis(), new NumberAxis()) {};
        HBox.getChildren().add(sensorChart2);
        HBox.setPrefWidth(width);
        sensorChart2.setPrefSize((width*5)/11, height);
    }

    @FXML
    public void updateSensorChart(LineChart sensorChart) {
        if(this.room != null){
            if(sensorChart != null) {
                sensorChart.getData().clear();
                try {
                    sensorChart.getData().addAll(getChartData(0));
                } catch (Exception e) {
                    autoUpdate = false;
                    new Alert(Alert.AlertType.ERROR, e.getMessage()).showAndWait();
                }
            } else {
                new Alert(Alert.AlertType.ERROR, "Line Chart Failed to Load" + "in room " + roomNumber).showAndWait();
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

    private ObservableList<XYChart.Series<Integer, Integer>> getChartData(int sensorID) throws NullPointerException{

        XYChart.Series<Integer, Integer> series = new XYChart.Series<>();
        if(this.room.getListOfSensors().isEmpty()){
            throw new NullPointerException("Room " + roomNumber + " has no sensors");
        }
        if(this.room.getListOfSensors().size() <= sensorID){
            throw new IllegalArgumentException("Requested sensor " + sensorID + " but room " + roomNumber + " has " +  this.room.getListOfSensors().size() + " sensors");
        }
        series.setName(this.room.getListOfSensors().get(sensorID).getType());
        this.title.setText("Room Number " + this.roomNumber);
        List<Integer> sensorReadings = this.room.getListOfSensors().get(sensorID).getHistoryLog();
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
                    if(autoUpdate) {
                        updateStandardSensorChart();
                    }
                });
            }
        }, 1, 2000 );
    }

    public void updateStandardSensorChart(){
        updateSensorChart(sensorChart);
    }
    public LineChart getSensorChart(){
        return this.sensorChart;
    }
}

package no.ntnu.idata2304.group1.clientapp.app2.ui;

import javafx.application.Platform;
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

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class RoomWindowController {
    public Label title;
    @FXML
    public FlowPane pane;
    public FlowPane HBox;
    public Label CurrentTemperature;
    @FXML
    private LineChart<String, Number> sensorChart;
    private Room room;
    private int roomNumber;

    boolean autoUpdate = true;


    public RoomWindowController(){
    }

    public void setRoom(Room room) {
        this.room = room;
        this.roomNumber = room.getRoomNumber();
        pane.setPrefSize(420, 420);
        pane.setStyle(
                "-fx-background-color: grey;" +
                "-fx-border-radius: 150px;");

        autoUpdateChart(sensorChart, 0);
    }

    public void expandRoomView(double height, double width){
        sensorChart.setPrefSize(width*10/21, height/2);
        HBox.setPrefWidth(width);
        for(int i = 1; i < room.getListOfSensors().size(); i++) {
            LineChart<String, Number> sensorChart2 = new LineChart<>(new CategoryAxis(), new NumberAxis()) {};
            HBox.getChildren().add(sensorChart2);
            sensorChart2.setPrefSize(width*10/21, height/2);
            autoUpdateChart(sensorChart2, i);
        }
    }

    public void contractRoomView(){
        sensorChart.setPrefSize(400, 350);
        pane.setPrefSize(420, 420);
        HBox.setPrefSize(420, 420);
        for(int i = 0; i < HBox.getChildren().size(); i++){
            try{
            HBox.getChildren().remove(1);
            } catch (Exception e){
                //TODO: fix this error
                System.out.println("Error closing all the sensors. Error in contractWindow in RoomWindowController");
            }
        }
    }

    @FXML
    public void updateSensorChart(LineChart sensorChart, int sensorID) {
        if(this.room != null){
            if(sensorChart != null) {
                sensorChart.getData().clear();
                try {
                    sensorChart.getData().addAll(getChartData(sensorID));
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

    public Room getRoom(){
        return this.room;
    }

    /**
     * Updates observable for line chart
     * @param sensorID the id of the sensor to get the data from
     * @return returns observable list
     */
    private ObservableList<XYChart.Series<String, Double>> getChartData(int sensorID){

        XYChart.Series<String, Double> series = new XYChart.Series<>();
        if(this.room.getListOfSensors().size() <= sensorID){
            throw new IllegalArgumentException("Requested sensor " + sensorID + " but room " + roomNumber + " has " +  this.room.getListOfSensors().size() + " sensors");
        }
        series.setName(this.room.getListOfSensors().get(sensorID).getTypeName());
        this.title.setText("Room Number " + this.roomNumber);
        List<SensorRecord> sensorReadings = this.room.getListOfSensors().get(sensorID).getHistoryLog();
        int i = 0;
        for (SensorRecord sensorReading : sensorReadings) {
            i++;
            String seconds = sensorReading.date().getSeconds() + "";
            if(sensorReading.date().getSeconds()  < 10){
                seconds = "0" + seconds;
            }
            series.getData().add(new XYChart.Data(sensorReading.date().getHours() + "." + sensorReading.date().getMinutes() + "." + seconds, sensorReading.value()));
            i++;
        }
        ObservableList<XYChart.Series<String, Double>> seriesList = FXCollections.observableArrayList();
        seriesList.addAll(series);

        return seriesList;
    }

    public Pane getPane(){
        return this.pane;
    }

    /**
     * Updates the chart every second
     */
    private void autoUpdateChart(LineChart<String, Number> sensorChart, int sensorID) {
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> {
                    if(autoUpdate) {
                        updateStandardSensorChart(sensorChart, sensorID);
                    }
                });
            }
        }, 1, 5000 );
    }

    public void updateStandardSensorChart(LineChart<String, Number> sensorChart, int sensorID){
        updateSensorChart(sensorChart, sensorID);
    }
}

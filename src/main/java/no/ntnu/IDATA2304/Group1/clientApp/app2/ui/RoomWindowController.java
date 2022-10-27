package no.ntnu.idata2304.group1.clientApp.app2.ui;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import no.ntnu.idata2304.group1.clientApp.app2.logic.Room;
import no.ntnu.idata2304.group1.clientApp.app2.logic.Sensor;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class RoomWindowController {
    public Label title;
    @FXML
    private LineChart sensorChart;
    @FXML
    private Label CurrentTemperature;
    @FXML
    private Sensor Sensor;
    @FXML
    private Room room;
    private int roomNumber;

    public RoomWindowController(){
        System.out.println("RoomWindowController");
        autoUpdateChart();
    }

    public void setRoom(Room room) {
        this.room = room;
        this.roomNumber = room.getRoomNumber();

    }

    public void updateCurrentTemperature(int temperature){
        //CurrentTemperature.setText(temperature + "");
    }


    @FXML
    public void updateSensorChart() {
        if(this.room != null){
            if(this.sensorChart != null) {
                System.out.println("Updating chart");
                this.sensorChart.getData().clear();
                try {
                    this.sensorChart.getData().addAll(getChartData());
                } catch (Exception e) {
                    new Alert(Alert.AlertType.ERROR, e.getMessage()).showAndWait();
                    System.out.println("Error updating chart am ount of sensors are" + room.getListOfSensors().size());
                }
            } else {
                System.out.println("sensorChart is null");
            }
        } else {
            System.out.println("------------------Could not update chart because room is null-------------------");
            System.out.println("RoomNumber = " + this.roomNumber + " Room = " + this.room + " Sensor = " + this.Sensor);
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
        if(this.room.getListOfSensors().size() < 1){
            throw new NullPointerException("Room " + roomNumber + " has no sensors");
        }
        series.setName(this.room.sensorList.get(0).getType());
        List<Integer> sensoreReadings = this.room.getListOfSensors().get(0).getHistoryLog();
        for (int i = 0; i < sensoreReadings.size(); i++) {
            series.getData().add(new XYChart.Data(Integer.valueOf(i), sensoreReadings.get(i)));
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

                    System.out.println("sensorlist size: " + getRoom().getListOfSensors().size());
                    updateSensorChart();
                });
            }
        }, 1000, 10000);
    }
}

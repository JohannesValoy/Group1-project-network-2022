package no.ntnu.IDATA2304.Group1.clientApp.app2.ui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import no.ntnu.IDATA2304.Group1.clientApp.app2.logic.Room;
import no.ntnu.IDATA2304.Group1.clientApp.app2.logic.Sensor;

import java.util.List;

public class RoomWindowController {
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
    }

    public void setRoom(Room room) {
        this.room = room;
        this.roomNumber = room.getRoomNumber();
        this.Sensor = room.getListOfSensors().get(0);

    }

    public void updateCurrentTemperature(int temperature){
        //CurrentTemperature.setText(temperature + "");
    }


    @FXML
    public void updateRainLevelChart() {
        int currentLevel = this.room.getListOfSensors().get(0).getCurrentLevel();
        //this.CurrentTemperature.setText(Integer.toString(currentLevel));
        // Update the Chart
        if(this.sensorChart != null) {
            this.sensorChart.getData().clear();
            this.sensorChart.getData().addAll(getChartData());
        } else {
            System.out.println("sensorChart is null");
        }

    }



    public Room getRoom(){
        return this.room;
    }

    private ObservableList<XYChart.Series<Integer, Integer>> getChartData() {

        XYChart.Series<Integer, Integer> series = new XYChart.Series<>();
        series.setName("Rain level");
        List<Integer> rainLevels = this.room.getListOfSensors().get(0).getHistoryLog();
        System.out.println(room.getRoomNumber() + " " + getRoom().getListOfSensors().get(0).getHistoryLog().get(0));
        for (int i = 0; i < rainLevels.size(); i++) {
            series.getData().add(new XYChart.Data(Integer.valueOf(i), rainLevels.get(i)));
        }
        ObservableList<XYChart.Series<Integer, Integer>> seriesList = FXCollections.observableArrayList();
        seriesList.addAll(series);

        return seriesList;
    }

    /**
     * Called when the user clicks on the line chart.
     *
     * @param mouseEvent mouse event when line chart is clicked.
     */
    @FXML
    public void lineChartClicked(MouseEvent mouseEvent) {
        updateRainLevelChart();
    }
}

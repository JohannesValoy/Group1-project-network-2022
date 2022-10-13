package no.ntnu.IDATA2304.Group1.clientApp.ui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import no.ntnu.IDATA2304.Group1.clientApp.weatherstation.RainSensor;

import java.util.List;

public class RainSensorController {
    @FXML
    private LineChart<Integer, Integer> rainSensorChart;
    @FXML
    private TextField rainLevelText;

    private RainSensor rainSensor;

    private Scene mainScene;

    public void initialize() {

    }

    public void setRainSensor(RainSensor rainSensor) {
        this.rainSensor = rainSensor;
    }

  public void setMainScene(Scene scene) {
    this.mainScene = scene;
  }

    public void returnHome(ActionEvent actionEvent) {
        Stage primaryStage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        primaryStage.hide();
        primaryStage.setScene(this.mainScene);
        primaryStage.show();
    }

    public void updateRainLevel(MouseEvent mouseEvent) {
        Integer currentLevel = this.rainSensor.getCurrentLevel();
        this.rainLevelText.setText(currentLevel.toString());

        // Update the Chart
        this.rainSensorChart.getData().clear();
        this.rainSensorChart.getData().addAll(getChartData());
    }

    /**
     *
     * @return ObservableList<XYChart.Series<String, Double>>
     */
    private ObservableList<XYChart.Series<Integer, Integer>> getChartData() {

        XYChart.Series<Integer, Integer> series = new XYChart.Series<>();

        series.setName("Rain level");

        List<Integer> rainLevels = this.rainSensor.getHistoryLog();
        for (int i = 0; i < rainLevels.size(); i++) {
            series.getData().add(new XYChart.Data(Integer.valueOf(i), rainLevels.get(i) ));
        }

        ObservableList<XYChart.Series<Integer, Integer>> seriesList = FXCollections.observableArrayList();
        seriesList.addAll(series);

        return seriesList;
    }
}

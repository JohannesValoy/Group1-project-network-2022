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
import jfxtras.scene.control.gauge.linear.SimpleMetroArcGauge;
import no.ntnu.IDATA2304.Group1.clientApp.weatherstation.RainSensor;
import no.ntnu.IDATA2304.Group1.clientApp.weatherstation.WindSensor;

import java.util.List;

public class RainSensorController {
    @FXML
    private LineChart<Integer, Integer> rainSensorChart;
    @FXML
    private TextField rainLevelText;

    private RainSensor rainSensor;

    private Scene mainScene;
    private WindSensor windSensor;
    @FXML
    private SimpleMetroArcGauge windSpeedDial;

    public void initialize() {

    }

    public RainSensor getRainSensor(RainSensor rainSensor) {
        return this.rainSensor;
    }

    public void setRainSensor(RainSensor rainSensor) {
        this.rainSensor = rainSensor;
    }

  public void setMainScene(Scene scene) {
    this.mainScene = scene;
  }

    public void updateRainLevel(MouseEvent mouseEvent) {
        Integer currentLevel = this.rainSensor.getCurrentLevel();

        // Update the Chart
        this.rainSensorChart.getData().clear();
        this.rainSensorChart.getData().addAll(getChartData());

        this.windSpeedDial.setValue(currentLevel);
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

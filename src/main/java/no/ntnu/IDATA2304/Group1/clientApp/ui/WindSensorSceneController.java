package no.ntnu.IDATA2304.Group1.clientApp.ui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import jfxtras.scene.control.gauge.linear.SimpleMetroArcGauge;
import no.ntnu.IDATA2304.Group1.clientApp.weatherstation.WindSensor;

public class WindSensorSceneController {

  @FXML
  private TextField windDirectionText;
  @FXML
  private TextField windSpeedText;
  @FXML
  private SimpleMetroArcGauge windSpeedDial;

  // The sensor to work with from the business layer
  private WindSensor windSensor;

  // The scene to return to
  private Scene mainScene;
  private Stage primaryStage;

  public void initialize() {
    //windSpeedDial.
    System.out.println("Initialize called...");
  }

  public void setWindSensor(WindSensor windSensor) {
    this.windSensor = windSensor;
  }

  public void setMainScene(Scene mainScene) {
    this.mainScene = mainScene;
  }

  public void setPrimaryStage(Stage primaryStage) {
    this.primaryStage = primaryStage;
  }

  public void mouseClickHandler(MouseEvent mouseEvent) {
    int windSpeed = this.windSensor.getWindSpeed();
    this.windSpeedDial.setValue(windSpeed);
    this.windSpeedText.setText(Integer.toString(windSpeed));
    this.windDirectionText.setText(Integer.toString(this.windSensor.getWindDirection()));
  }

  public void returnHome(ActionEvent actionEvent) {
    Stage primaryStage = (Stage)((Node) actionEvent.getSource()).getScene().getWindow();
    primaryStage.hide();
    primaryStage.setScene(this.mainScene);
    primaryStage.show();
  }
}

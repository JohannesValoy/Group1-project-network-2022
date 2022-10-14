package no.ntnu.IDATA2304.Group1.clientApp.ui;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;


public class MainSceneController {
  @FXML
  public GridPane gridPane;
  @FXML
  private Scene rainSensorScene;
  @FXML
  private Scene windSensorScene;

  public MainSceneController(){
  }

  public void setRainSensorScene(Scene rainSensorScene) {
    this.rainSensorScene = rainSensorScene;
  }

  public void setWindSensorScene(Scene windSensorScene) {
    this.windSensorScene = windSensorScene;
  }

  public void openRainSensorScene() {
    this.setScene(new Stage(), this.rainSensorScene);
  }

  public void openWindSensorScene() {
    this.setScene(new Stage(), this.windSensorScene);
  }

  void setScene(Stage primaryStage, Scene newScene) {
    primaryStage.hide();
    primaryStage.setScene(newScene);
    primaryStage.show();
  }

  public GridPane getGridPane() {
    return this.gridPane;
  }
}

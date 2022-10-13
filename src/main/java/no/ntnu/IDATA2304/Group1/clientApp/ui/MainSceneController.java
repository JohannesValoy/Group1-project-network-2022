package no.ntnu.IDATA2304.Group1.clientApp.ui;

import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainSceneController {

  private Scene windSensorScene;
  private Scene rainSensorScene;

  public void setWindSensorScene(Scene windSensorScene) {
    this.windSensorScene = windSensorScene;
  }

  public void setRainSensorScene(Scene rainSensorScene) {
    this.rainSensorScene = rainSensorScene;
  }


  public void openWindSensorScene(ActionEvent actionEvent) {
    Stage primaryStage = (Stage)((Node) actionEvent.getSource()).getScene().getWindow();
    this.setScene(primaryStage, this.windSensorScene);
  }

  public void openRainSensorScene(ActionEvent actionEvent) {
    Stage primaryStage = (Stage)((Node) actionEvent.getSource()).getScene().getWindow();
    this.setScene(primaryStage, this.rainSensorScene);
  }

  private void setScene(Stage primaryStage, Scene newScene) {
    primaryStage.hide();
    primaryStage.setScene(newScene);
    primaryStage.show();
  }

}

package no.ntnu.IDATA2304.Group1.clientApp.ui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import jfxtras.scene.control.gauge.linear.SimpleMetroArcGauge;
import no.ntnu.IDATA2304.Group1.clientApp.weatherstation.WeatherStation;

import java.io.IOException;
import java.util.ArrayList;

public class MainWindow extends Application {

  // The business logic to communicate with
  private WeatherStation weatherStation;

  // The primary stage
  private Stage primaryStage;

  // The different scenes making up the application
  private Scene mainScene;
  private Scene windSensorScene;
  private Scene rainSensorScene;
  MainSceneController mainSceneController;
  FXMLLoader mainPaneLoader;
  ArrayList<Node> rooms;
  ArrayList<Scene> scenes;

  @Override
  public void start(Stage primaryStage) throws Exception {
    this.primaryStage = primaryStage;
    this.rooms = new ArrayList<>();
    this.scenes = new ArrayList<>();
    scenes.add(makeRainScene());

    this.mainSceneController.addRooms(4, sc);
    this.mainSceneController = new MainSceneController(rooms);

    // First create the business logic to work with
    this.weatherStation = new WeatherStation();

    // Getting loader and a pane for the main scene.
    // Loader will then give a possibility to get related controller
    mainPaneLoader = new FXMLLoader(getClass().getResource("MainScene.fxml"));
    Parent mainPane = mainPaneLoader.load();
    this.mainScene = new Scene(mainPane, 1000, 750);


    // Getting loader and a pane for the Wind Sensor scene
    FXMLLoader windSensorPageLoader = new FXMLLoader(getClass().getResource("WindSensorScene.fxml"));
    Parent windSensorPane = windSensorPageLoader.load();
    this.windSensorScene = new Scene(windSensorPane, 300, 275);
    WindSensorSceneController windSensorSceneController = windSensorPageLoader.getController();
    windSensorSceneController.setWindSensor(this.weatherStation.getWindSensor());
    // Inject/set the main scene to return to from the wind sensor scene
    windSensorSceneController.setMainScene(this.mainScene);
    windSensorSceneController.setPrimaryStage(this.primaryStage);


    // Getting loader and a pane for the rain sensor scene
    FXMLLoader rainSensorPageLoader = new FXMLLoader(getClass().getResource("RainSensorScene.fxml"));
    Parent rainSensorPane = rainSensorPageLoader.load();
    this.rainSensorScene = new Scene(rainSensorPane, 700, 500);
    RainSensorController rainSensorController = rainSensorPageLoader.getController();
    rainSensorController.setRainSensor(this.weatherStation.getRainSensor());
    // Inject/set the main scene to return to from the wind sensor scene
    //rainSensorController.setMainScene(this.mainScene);
    rainSensorController.setMainScene(this.windSensorScene);

    // Injecting wind sensor scene and rain sensor scene into the controller of the main scene
    mainSceneController = mainPaneLoader.getController();
    mainSceneController.setWindSensorScene(this.windSensorScene);
    mainSceneController.setRainSensorScene(this.rainSensorScene);





    primaryStage.setTitle("Weather Station");
    primaryStage.setMinHeight(600);
    primaryStage.setMinWidth(1000);
    primaryStage.setScene(this.mainScene);
    primaryStage.show();
    mainSceneController.addRooms(4, );
  }

  public Scene makeRainScene() throws IOException {

    // Getting loader and a pane for the rain sensor scene
    FXMLLoader rainSensorPageLoader = new FXMLLoader(getClass().getResource("RainSensorScene.fxml"));
    Parent rainSensorPane = rainSensorPageLoader.load();
    this.rainSensorScene = new Scene(rainSensorPane, 700, 500);
    RainSensorController rainSensorController = rainSensorPageLoader.getController();
    rainSensorController.setRainSensor(this.weatherStation.getRainSensor());
    // Inject/set the main scene to return to from the wind sensor scene
    //rainSensorController.setMainScene(this.mainScene);
    rainSensorController.setMainScene(this.windSensorScene);

    return this.rainSensorScene;
  }



  public static void main(String[] args) {
    launch(args);
  }
}

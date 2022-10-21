package no.ntnu.IDATA2304.Group1.clientApp.app2.ui;

import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.layout.FlowPane;

/**
 * makes a window with data with a gridpane for every room.
 */

public class MainWindowController {

    public FlowPane flowPane;
    public javafx.scene.layout.BorderPane BorderPane;

    @FXML
    private LineChart<Integer, Integer> rainSensorChart;

    public MainWindowController(){

    }

    public FlowPane getFlowPane(){
        return this.flowPane;
    }

}

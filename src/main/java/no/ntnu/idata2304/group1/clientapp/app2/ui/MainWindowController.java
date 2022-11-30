package no.ntnu.idata2304.group1.clientapp.app2.ui;

import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.FlowPane;

/**
 * Makes a window with data with a grid pane for every room.
 * 
 */

public class MainWindowController {

    @FXML
    private FlowPane flowPane;
    @FXML
    public javafx.scene.layout.BorderPane BorderPane;
    @FXML
    private FlowPane mainFlowPane;

    @FXML
    private LineChart<Integer, Integer> sensorChart;

    public MainWindowController() {}

    /**
     * returns the flow pane
     *
     * @return flow pane
     */
    public FlowPane getFlowPane() {
        return this.flowPane;
    }

}

package no.ntnu.idata2304.group1.clientapp.app2.ui;

import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.FlowPane;

/**
 * makes a window with data with a gridpane for every room.
 */

public class MainWindowController {

    public FlowPane flowPane;
    public javafx.scene.layout.BorderPane BorderPane;
    public ScrollPane scrollPane;

    @FXML
    private LineChart<Integer, Integer> sensorChart;

    public MainWindowController(){
    }

    /**
     * returns the flowpane
     *
     * @return flowpane
     */
    public FlowPane getFlowPane(){
        return this.flowPane;
    }

}

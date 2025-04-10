package app.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class MainMenuController {
    @FXML
    private Button startButton;
    @FXML
    private Button fromFileButton;
    @FXML
    private Button uploadManuallyButton;
    @FXML
    private Button fromPastScenarioButton;


    @FXML
    public void initialize() {
        startButton.setDisable(true);

        fromFileButton.setOnAction(event -> {
            startButton.setDisable(false);
        });

        uploadManuallyButton.setOnAction(event -> {
            startButton.setDisable(false);
        });

        fromPastScenarioButton.setOnAction(event -> {
            startButton.setDisable(false);
        });
    }
}
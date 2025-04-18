package app.controller;

import app.model.Field;
import app.model.ShireMap;
import app.util.DataLoader;
import app.util.SceneManager;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

import java.awt.*;

public class MainMenuController {

    private ShireMap shireMap;

    @FXML
    private HBox titleBar;
    @FXML
    private Button startButton;
    @FXML
    private Button fromFileButton;
    @FXML
    private Button uploadManuallyButton;
    @FXML
    private Button fromPastScenarioButton;

    private double xOffset = 0;
    private double yOffset = 0;


    public MainMenuController(ShireMap shireMap) {
        this.shireMap = shireMap;
    }

    @FXML
    public void initialize() {
        startButton.setDisable(true);

//        titleBar.setOnMousePressed((MouseEvent event) -> {
//            xOffset = event.getSceneX();
//            yOffset = event.getSceneY();
//        });
//
//        titleBar.setOnMouseDragged((MouseEvent event) -> {
//            Stage stage = (Stage) titleBar.getScene().getWindow();
//            stage.setX(event.getScreenX() - xOffset);
//            stage.setY(event.getScreenY() - yOffset);
//        });

        fromFileButton.setOnAction(event -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Wybierz plik");
            File file = fileChooser.showOpenDialog(SceneManager.getStage());

            if (file != null) {
                DataLoader dataLoader = new DataLoader(shireMap);
                dataLoader.loadFromFile(file.getAbsolutePath());
            }

            startButton.setDisable(false);
        });

        uploadManuallyButton.setOnAction(event -> {
            startButton.setDisable(false);
        });

        fromPastScenarioButton.setOnAction(event -> {
            startButton.setDisable(false);
        });

        startButton.setOnAction(event -> {

            shireMap.simulateWholeProcess();

        });
    }
}
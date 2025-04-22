package app.controller;

import app.model.ShireMap;
import app.util.DataLoader;
import app.util.SceneManager;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.FileChooser;
import java.io.File;


public class MainMenuController {

    private ShireMap shireMap;

    @FXML
    private Button startButton;
    @FXML
    private Button fromFileButton;
    @FXML
    private Button uploadManuallyButton;
    @FXML
    private Button fromPastScenarioButton;


    public MainMenuController(ShireMap shireMap) {
        this.shireMap = shireMap;
    }

    @FXML
    public void initialize() {
        startButton.setDisable(true);

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
            SceneManager.switchScene("/fxml/input-menu.fxml", "/styles/input-menu.css");
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
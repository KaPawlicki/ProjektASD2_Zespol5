package app.controller;

import app.model.structure.ShireMap;
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

    public void changeStartButton() {
        startButton.setDisable(!shireMap.isNotEmpty());
    }

    @FXML
    public void initialize() {
        changeStartButton();
        shireMap.print();

        //obsluga przycisku do wczytywania z pliku
        fromFileButton.setOnAction(event -> {
            shireMap.clear();
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Wybierz plik");
            File file = fileChooser.showOpenDialog(SceneManager.getStage());

            if (file != null) {
                DataLoader dataLoader = new DataLoader(shireMap);
                dataLoader.loadFromFile(file.getAbsolutePath());
            }
            changeStartButton();
        });


        //obluga przycisku do wpisywania recznego
        uploadManuallyButton.setOnAction(event -> {
            SceneManager.switchScene("/fxml/input-menu.fxml", "/styles/input-menu.css");
            changeStartButton();
        });

        //obsluga przycisku do wczytywania poprzednich symulacji
        fromPastScenarioButton.setOnAction(event -> {

        });

        //obsluga przycisku start
        startButton.setOnAction(event -> {
            shireMap.simulateWholeProcess();
            System.out.println("========");
            shireMap.simulateWholeProcessWithActivation();
        });
    }
}
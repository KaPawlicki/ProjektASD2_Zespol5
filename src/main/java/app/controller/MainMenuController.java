package app.controller;

import app.model.structure.ShireMap;
import app.util.DataLoader;
import app.util.SceneManager;
import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.FileChooser;
import javafx.util.Duration;

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
    @FXML
    private Label toastMessage;


    public MainMenuController(ShireMap shireMap) {
        this.shireMap = shireMap;
    }

    @FXML
    public void initialize() {
        changeStartButton();

        //obsluga przycisku do wczytywania z pliku
        fromFileButton.setOnAction(event -> {
            readFromFile();
            showToast();
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

    public void changeStartButton() {
        startButton.setDisable(shireMap.isEmpty());
    }

    public void readFromFile() {
        shireMap.clear();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Wybierz plik");
        File file = fileChooser.showOpenDialog(SceneManager.getStage());

        if (file != null) {
            DataLoader dataLoader = new DataLoader(shireMap);
            dataLoader.loadFromFile(file.getAbsolutePath());
        }
        changeStartButton();
    }

    public void showToast(){
        toastMessage.setText("Wczytano " + shireMap.getNumberOfNodes() + " wierzcholków i " +
                shireMap.getNumberOfEdges() + " krawędzi z pliku");

        FadeTransition fadeIn = new FadeTransition(Duration.seconds(1.0), toastMessage);
        fadeIn.setFromValue(0.0);
        fadeIn.setToValue(1.0);

        FadeTransition fadeOut = new FadeTransition(Duration.seconds(1.5), toastMessage);
        fadeOut.setFromValue(1.0);
        fadeOut.setToValue(0.0);
        fadeOut.setDelay(Duration.seconds(3));

        fadeIn.setOnFinished(event -> fadeOut.play());

        fadeIn.play();
    }
}
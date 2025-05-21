package app.controller;

import app.model.structure.ShireMap;
import app.util.DataLoader;
import app.util.DataWriter;
import app.util.SceneManager;
import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.FileChooser;
import javafx.util.Duration;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


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
    private Button searchInResultsButton;
    @FXML
    private Label toastMessage;


    public MainMenuController(ShireMap shireMap) {
        this.shireMap = shireMap;
    }

    @FXML
    public void initialize() {
        ensureDirectoriesExist();
        changeStartButton();

        //obsluga przycisku do wczytywania z pliku
        fromFileButton.setOnAction(event -> {
            readFromFile("");
            showToast();
        });

        //obluga przycisku do wpisywania recznego
        uploadManuallyButton.setOnAction(event -> {
            SceneManager.switchScene("/fxml/input-menu.fxml", "/styles/input-menu.css");
            changeStartButton();
        });

        //obsluga przycisku do wczytywania poprzednich symulacji
        fromPastScenarioButton.setOnAction(event -> {
            readFromFile("src/main/savedSimulations");
            showToast();
        });

        searchInResultsButton.setOnAction(event -> {
            SceneManager.switchScene("/fxml/searching-screen.fxml", "/styles/searching-screen.css");
        });

        //obsluga przycisku start
        startButton.setOnAction(event -> {
            saveToFile();
//            System.out.println(shireMap.simulateWholeProcess());
//            System.out.println(shireMap.simulateWholeProcessWithActivation());
            SceneManager.switchScene("/fxml/simulation-result-screen.fxml", "/styles/simulation-result-screen.css");
        });
    }

    public void changeStartButton() {
        startButton.setDisable(shireMap.isEmpty());
    }

    public static void ensureDirectoriesExist() {
        createDirectoryIfMissing("src/main/savedSimulations/");
        createDirectoryIfMissing("src/main/simulationResults/");
    }

    private static void createDirectoryIfMissing(String path) {
        File directory = new File(path);
        if (!directory.exists()) {
            directory.mkdirs();
        }
    }

    public void readFromFile(String initialPath) {
        shireMap.clear();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Wybierz plik");

        File initialDir = new File(initialPath);
        if (initialDir.exists() && initialDir.isDirectory()) {
            fileChooser.setInitialDirectory(initialDir);
        }

        File file = fileChooser.showOpenDialog(SceneManager.getStage());

        if (file != null) {
            DataLoader dataLoader = new DataLoader(shireMap);
            dataLoader.loadFromFile(file.getAbsolutePath());
        }
        changeStartButton();
    }

    public void saveToFile() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yy_HH-mm");
        String formatted = now.format(formatter);

        String filename = "src/main/savedSimulations/" + formatted + ".txt";

        DataWriter dataWriter = new DataWriter(shireMap);
        dataWriter.writeToFile(filename);
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
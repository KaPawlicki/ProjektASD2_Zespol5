package app.controller;

import app.model.algorithm.text.BoyerMoore;
import app.model.algorithm.text.Kmp;
import app.model.algorithm.text.NaiveStringSearch;
import app.model.algorithm.text.RabinKarp;
import app.model.algorithm.text.huffman.HuffmanCoding;
import app.model.structure.ShireMap;
import app.util.SceneManager;
import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class SearchingScreenController {
    private ShireMap shireMap;
    private List<Integer> occurrences;
    private int occurrenceIndex = 0;
    private String fileContents;
    private Boolean resultVisible = false;

    @FXML
    private AnchorPane root;
    @FXML
    private Button exitButton;
    @FXML
    private AnchorPane bottomContainer;
    @FXML
    private ToggleGroup algorithmChoice;
    @FXML
    private TextField searchField;
    @FXML
    private Button searchButton;
    @FXML
    private Text textBeforeFoundWord;
    @FXML
    private Text foundWord;
    @FXML
    private Text textAfterFoundWord;
    @FXML
    private Button nextButton;
    @FXML
    private Button prevButton;
    @FXML
    private Label occurrenceCounter;
    @FXML
    private Label errorMessage;

    public SearchingScreenController(ShireMap shireMap) {
        this.shireMap = shireMap;
    }

    private void search() {
        if (searchField.getText().isEmpty()) {
            showErrorMessage("Pole wyszukiwania nie moze byc puste");
            return;
        }
        if (!Files.exists(Path.of("src/main/simulationResults/compressedResults.huf"))) {
            showErrorMessage("Nie ma jeszcze zadnych danych");
            return;
        }

        if (algorithmChoice.getSelectedToggle() == null) {
            showErrorMessage("Nie wybrano algorytmu");
            return;
        }

        if (fileContents == null) {
            decompressFile();
        }

        findAllOccurrences();
        if (!(occurrences == null) && !occurrences.isEmpty()) {
            showResultBox();
            occurrenceIndex = 0;
            showNextOccurrence(0);
        } else {
            showErrorMessage("Brak wyników wyszukiwania");
        }
    }

    private void decompressFile(){
        if (Files.exists(Path.of("src/main/simulationResults/compressedResults.huf"))) {
            fileContents = HuffmanCoding.decompress("src/main/simulationResults/compressedResults.huf");
        } else {
            showErrorMessage("Brak zapisanych rozwiązań");
        }
    }

    private void findAllOccurrences() {
        Toggle selectedToggle = algorithmChoice.getSelectedToggle();
        RadioButton selectedRadioButton = (RadioButton) selectedToggle;
        String selectedAlgorithm = selectedRadioButton.getText();

        switch (selectedAlgorithm){
            case "Rabin-Karp":
                occurrences = RabinKarp.search(searchField.getText(), fileContents);
                break;
            case "KMP":
                occurrences = Kmp.search(searchField.getText(), fileContents);
                break;
            case "Boyer-Moore":
                occurrences = BoyerMoore.search(searchField.getText(), fileContents);
                break;
            case "Algorytm naiwny":
                occurrences = NaiveStringSearch.search(searchField.getText(), fileContents);
                break;
        }
    }


    private void showNextOccurrence(int value) {
        //Obsluga indeksu
        if (occurrenceIndex == occurrences.size() - 1 && value == 1) {
            occurrenceIndex = 0;
        } else if (occurrenceIndex == 0 && value == -1) {
            occurrenceIndex = occurrences.size() - 1;
        } else {
            occurrenceIndex += value;
        }

        String searchText = searchField.getText();
        int wordLength = searchText.length();

        int foundWordStart = occurrences.get(occurrenceIndex);

        // Zbuduj listę wszystkich linii z pliku
        String[] lines = fileContents.split("\n", -1);

        // Znajdź do której linii należy początek znalezionego słowa
        int charCount = 0;
        int foundLineIndex = -1;
        for (int i = 0; i < lines.length; i++) {
            int lineLength = lines[i].length() + 1; // +1 bo '\n'
            if (foundWordStart < charCount + lineLength) {
                foundLineIndex = i;
                break;
            }
            charCount += lineLength;
        }

        if (foundLineIndex == -1) return;

        // Pobierz do linii
        int startLine = Math.max(0, foundLineIndex - 2);
        int endLine = Math.min(lines.length, startLine + 5);

        StringBuilder displayText = new StringBuilder();
        for (int i = startLine; i < endLine; i++) {
            displayText.append(lines[i]);
            if (i < endLine - 1) displayText.append("\n");
        }

        // Wyznacz offset lokalny dla wyszukiwanego słowa w displayText
        int globalOffset = 0;
        for (int i = 0; i < startLine; i++) {
            globalOffset += lines[i].length() + 1; // '\n'
        }

        int localFoundStart = foundWordStart - globalOffset;
        int localFoundEnd = localFoundStart + wordLength;

        // Rozdziel tekst na before / found / after
        String visibleText = displayText.toString();
        if (localFoundStart < 0 || localFoundEnd > visibleText.length()) {
            textBeforeFoundWord.setText("");
            foundWord.setText(searchText);
            textAfterFoundWord.setText("");
        } else {
            textBeforeFoundWord.setText(visibleText.substring(0, localFoundStart));
            foundWord.setText(visibleText.substring(localFoundStart, localFoundEnd));
            textAfterFoundWord.setText(visibleText.substring(localFoundEnd));
        }

        // Zmiana licznika
        occurrenceCounter.setText(occurrenceIndex+1 + " z " + occurrences.size() + " wystapien");
    }

    public void showErrorMessage(String text){
        bottomContainer.setStyle("-fx-opacity: 0;");
        errorMessage.setVisible(true);
        resultVisible = false;
        errorMessage.setText(text);

        FadeTransition fadeIn = new FadeTransition(Duration.seconds(0.5), errorMessage);
        fadeIn.setFromValue(0.0);
        fadeIn.setToValue(1.0);

        fadeIn.play();

        searchField.requestFocus();
    }

    public void showResultBox(){
        bottomContainer.setStyle("-fx-opacity: 1;");
        errorMessage.setVisible(false);
        resultVisible = true;
        root.requestFocus();
    }

    @FXML
    public void initialize() {
        Platform.runLater(() -> root.requestFocus());

        //obluga klawiatury
        root.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ESCAPE) {
                //wyjscie
                SceneManager.switchScene("/fxml/main-menu.fxml", "/styles/main-menu.css");
            } else if (event.getCode() == KeyCode.ENTER) {
                //wyszukiwanie
                search();
            }
            //nawigacja miedzy wysatapieniami
            if(resultVisible){
                if (event.getCode() == KeyCode.LEFT) {
                    showNextOccurrence(-1);
                }
                if (event.getCode() == KeyCode.RIGHT) {
                    showNextOccurrence(1);
                }
            }
        });
        root.setFocusTraversable(true);

        //enter w polu wyszukiwania
        searchField.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                search();
            }
        });

        //przycisk wyjscia
        exitButton.setOnAction(event -> {
            SceneManager.switchScene("/fxml/main-menu.fxml", "/styles/main-menu.css");
        });

        //przycisk wyszukiwania
        searchButton.setOnAction(event -> {
            search();
        });

        //nawigacja miedzy wysatapieniami
        nextButton.setOnAction(event -> {
            showNextOccurrence(1);
            root.requestFocus();
        });

        prevButton.setOnAction(event -> {
            showNextOccurrence(-1);
            root.requestFocus();
        });

        //zwrocenie focusa rootowi przez radio
        Set<Node> radios = root.lookupAll(".radioButton");
        for (Node node : radios) {
            if (node instanceof RadioButton rb) {
                rb.setOnAction(e -> Platform.runLater(() -> root.requestFocus()));
            }
        }
    }
}

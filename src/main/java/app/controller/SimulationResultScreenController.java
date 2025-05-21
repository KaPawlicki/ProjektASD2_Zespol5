package app.controller;

import app.model.algorithm.text.huffman.HuffmanCoding;
import app.model.structure.ShireMap;
import app.util.SceneManager;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;
import javafx.util.Pair;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SimulationResultScreenController {
    private ShireMap shireMap;
    private char lastSpeaker = 'S';
    private Timeline typingTimeline = null;
    private boolean typingInProgress = false;
    private boolean spaceLocked = false;
    private List<Pair<Character, String>> speechList = new ArrayList<>();
    private int speechIndex = 0;
    @FXML
    private AnchorPane root;
    @FXML
    private ImageView samwiseImage;
    @FXML
    private StackPane samwiseTextField;
    @FXML
    private Label samwiseText;
    @FXML
    private ImageView advisorImage;
    @FXML
    private StackPane advisorTextField;
    @FXML
    private Label advisorText;
    @FXML
    private Label toastMessage;




    public SimulationResultScreenController(ShireMap shireMap) {
        this.shireMap = shireMap;
    }

    private void showNextDialog(char speaker, String text) {
        if (speaker == 'S') {
            samwiseText.setText("");
            if (speaker != lastSpeaker) {
                FadeTransition advisorFadeOut = new FadeTransition(Duration.seconds(0.7), advisorImage);
                advisorFadeOut.setFromValue(1.0);
                advisorFadeOut.setToValue(0.0);
                FadeTransition advisorTextFadeOut = new FadeTransition(Duration.seconds(0.7), advisorTextField);
                advisorTextFadeOut.setFromValue(1.0);
                advisorTextFadeOut.setToValue(0.0);

                FadeTransition samwiseFadeIn = new FadeTransition(Duration.seconds(0.7), samwiseImage);
                samwiseFadeIn.setFromValue(0.0);
                samwiseFadeIn.setToValue(1.0);
                FadeTransition samwiseTextFadeIn = new FadeTransition(Duration.seconds(0.7), samwiseTextField);
                samwiseTextFadeIn.setFromValue(0.0);
                samwiseTextFadeIn.setToValue(1.0);

                advisorFadeOut.setOnFinished(event -> samwiseFadeIn.play());
                advisorTextFadeOut.setOnFinished(event -> samwiseTextFadeIn.play());
                samwiseTextFadeIn.setOnFinished(event -> typeText(text, samwiseText, Duration.millis(50)));
                advisorFadeOut.play();
                advisorTextFadeOut.play();
            } else {
                typeText(text, samwiseText, Duration.millis(50));
            }

            lastSpeaker = speaker;
        } else if (speaker == 'A') {
            advisorText.setText("");
            if (speaker != lastSpeaker) {
                FadeTransition samwiseFadeOut = new FadeTransition(Duration.seconds(0.7), samwiseImage);
                samwiseFadeOut.setFromValue(1.0);
                samwiseFadeOut.setToValue(0.0);
                FadeTransition samwiseTextFadeOut = new FadeTransition(Duration.seconds(0.7), samwiseTextField);
                samwiseTextFadeOut.setFromValue(1.0);
                samwiseTextFadeOut.setToValue(0.0);

                FadeTransition advisorFadeIn = new FadeTransition(Duration.seconds(0.7), advisorImage);
                advisorFadeIn.setFromValue(0.0);
                advisorFadeIn.setToValue(1.0);
                FadeTransition advisorTextFadeIn = new FadeTransition(Duration.seconds(0.7), advisorTextField);
                advisorTextFadeIn.setFromValue(0.0);
                advisorTextFadeIn.setToValue(1.0);


                samwiseFadeOut.setOnFinished(event -> advisorFadeIn.play());
                samwiseTextFadeOut.setOnFinished(event -> advisorTextFadeIn.play());
                advisorTextFadeIn.setOnFinished(event -> typeText(text, advisorText, Duration.millis(50)));
                samwiseFadeOut.play();
                samwiseTextFadeOut.play();
            } else {
                typeText(text, advisorText, Duration.millis(50));
            }

            lastSpeaker = speaker;
        }
    }

    private void beginingAnimation() {
        advisorImage.setStyle("-fx-opacity: 0");
        advisorTextField.setStyle("-fx-opacity: 0");
        samwiseImage.setStyle("-fx-opacity: 0");
        samwiseTextField.setStyle("-fx-opacity: 0");

        spaceLocked = true;
        PauseTransition unlock = new PauseTransition(Duration.millis(5000));
        unlock.setOnFinished(e -> spaceLocked = false);
        unlock.play();

        FadeTransition sceneFadeIn = new FadeTransition(Duration.seconds(3.0), root);
        sceneFadeIn.setFromValue(0.0);
        sceneFadeIn.setToValue(1.0);

        FadeTransition samwiseFadeIn = new FadeTransition(Duration.seconds(1.1), samwiseImage);
        samwiseFadeIn.setFromValue(0.0);
        samwiseFadeIn.setToValue(1.0);

        FadeTransition samwiseTextFadeIn = new FadeTransition(Duration.seconds(0.9), samwiseTextField);
        samwiseTextFadeIn.setFromValue(0.0);
        samwiseTextFadeIn.setToValue(1.0);

        sceneFadeIn.setOnFinished(event -> samwiseFadeIn.play());
        samwiseFadeIn.setOnFinished(event -> samwiseTextFadeIn.play());
        samwiseTextFadeIn.setOnFinished(event -> typeText(speechList.getFirst().getValue(), samwiseText, Duration.millis(50)));

        showToast();
        sceneFadeIn.play();

    }

    public void showToast(){
        toastMessage.setText("Aby kontynuować wciśnij spacje");

        FadeTransition fadeIn = new FadeTransition(Duration.seconds(1.0), toastMessage);
        fadeIn.setFromValue(0.0);
        fadeIn.setToValue(1.0);

        FadeTransition fadeOut = new FadeTransition(Duration.seconds(1.5), toastMessage);
        fadeOut.setFromValue(1.0);
        fadeOut.setToValue(0.0);
        fadeOut.setDelay(Duration.seconds(3));

        fadeIn.setOnFinished(event -> fadeOut.play());

        fadeIn.setDelay(Duration.seconds(5));
        fadeIn.play();
    }

    private void typeText(String fullText, Label targetLabel, Duration speedPerChar) {
        if (typingInProgress) {
            if (typingTimeline != null) {
                typingTimeline.stop();
            }
            targetLabel.setText(fullText);
            typingInProgress = false;
            return;
        }

        targetLabel.setText("");
        final StringBuilder displayedText = new StringBuilder();
        typingTimeline = new Timeline();
        typingInProgress = true;

        for (int i = 0; i < fullText.length(); i++) {
            final int index = i;
            KeyFrame keyFrame = new KeyFrame(speedPerChar.multiply(i), event -> {
                displayedText.append(fullText.charAt(index));
                targetLabel.setText(displayedText.toString());
            });
            typingTimeline.getKeyFrames().add(keyFrame);
        }

        typingTimeline.setOnFinished(e -> typingInProgress = false);
        typingTimeline.play();
    }

    //wystarczy w odpowiedniej kolejnosci wpisac to co ktora posatc ma mowic
    private void fillSpeechList() {
        speechList.add(new Pair<>('S', "przykladowy tekst numer 1 - samwise"));
        speechList.add(new Pair<>('S', "przykladowy tekst numer 2 - samwise"));
        speechList.add(new Pair<>('A', "przykladowy tekst numer 1 - advisor"));
        speechList.add(new Pair<>('S', "przykladowy tekst numer 3 - samwise"));
        speechList.add(new Pair<>('A', "przykladowy tekst numer 2 - advisor"));
        speechList.add(new Pair<>('S', "przykladowy tekst numer 4 - samwise"));
        speechList.add(new Pair<>('S', "przykladowy tekst numer 5 - samwise"));
        speechList.add(new Pair<>('S', "przykladowy tekst numer 6 - samwise"));
        speechList.add(new Pair<>('A', "przykladowy tekst numer 3 - advisor"));
        speechList.add(new Pair<>('S', "przykladowy tekst numer 7 - samwise"));
        speechList.add(new Pair<>('A', "przykladowy tekst numer 4 - advisor"));
        speechList.add(new Pair<>('S', "koniec symulacji - samwise"));
    }

    private void addResultToCompressedFile(){
        String joinedSpeech = speechList.stream()
                .map(Pair::getValue)
                .collect(Collectors.joining("\n"));

        if(Files.exists(Path.of("src/main/simulationResults/compressedResults.huf"))){
            HuffmanCoding.compress(
                    HuffmanCoding.decompress("src/main/simulationResults/compressedResults.huf")
                            + "\n\n" +
                            joinedSpeech,
                    "src/main/simulationResults/compressedResults.huf"
            );
        } else {
            HuffmanCoding.compress(joinedSpeech, "src/main/simulationResults/compressedResults.huf");
        }
    }

    @FXML
    public void initialize() {
        // Wypełnia listę dialogów
        fillSpeechList();

        root.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.SPACE) {
                if(spaceLocked) return; // ignoruj spację, jeśli blokada jest aktywne (np. w trakcie animacji zmiany postaci)

                if (typingInProgress) {
                    // Jeśli animacja pisania trwa, przerywa ją i wyświetla pełny tekst od razu
                    if (lastSpeaker == 'S') {
                        typeText(speechList.get(speechIndex).getValue(), samwiseText, Duration.millis(50));
                    } else {
                        typeText(speechList.get(speechIndex).getValue(), advisorText, Duration.millis(50));
                    }
                } else if (speechIndex == speechList.size() - 1) {
                    // Koniec wszystkich dialogów – zapisanie wyników i powrót do menu głównego
                    addResultToCompressedFile();
                    shireMap.clear();
                    SceneManager.switchScene("/fxml/main-menu.fxml", "/styles/main-menu.css");
                } else {
                    // Przejście do kolejnego dialogu
                    speechIndex++;

                    // Jeśli zmienia się mówca, blokujemy spację na czas przejścia animacji
                    if (speechList.get(speechIndex).getKey() != lastSpeaker) {
                        spaceLocked = true;
                        PauseTransition unlock = new PauseTransition(Duration.millis(1400));
                        unlock.setOnFinished(e -> spaceLocked = false);
                        unlock.play();
                    }

                    // Wywołuje dialog
                    showNextDialog(
                            speechList.get(speechIndex).getKey(),
                            speechList.get(speechIndex).getValue()
                    );
                }
            } else if (event.getCode() == KeyCode.ESCAPE) {
                // ESC – szybki powrót do menu głównego, niezależnie od etapu dialogu
                SceneManager.switchScene("/fxml/main-menu.fxml", "/styles/main-menu.css");
            }
        });

        // Umożliwia reagowanie na klawiaturę zaraz po uruchomieniu widoku
        root.setFocusTraversable(true);
        Platform.runLater(() -> root.requestFocus());

        // Początkowa animacja sceny oraz pierwszego dialogu
        beginingAnimation();
    }

}

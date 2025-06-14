package app.controller;

import app.model.algorithm.text.huffman.HuffmanCoding;
import app.model.simulation.AlgorithmRunner;
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
        toastMessage.setText("Aby kontynuować wciśnij dowolny klawisz");

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

    private void startThread(){
        AlgorithmRunner a1 = new AlgorithmRunner(this.shireMap, "N");
        AlgorithmRunner a2 = new AlgorithmRunner(this.shireMap, "A");
        AlgorithmRunner a3 = new AlgorithmRunner(this.shireMap, "Q");
        Thread thread1 = new Thread(a1);
        Thread thread2 = new Thread(a2);
        Thread thread3 = new Thread(a3);

        thread1.start();
        thread2.start();
        thread3.start();

        try {
            thread1.join();
            thread2.join();
            thread3.join();
            speechList.set(5, new Pair<>('A', "Oto, co udało się ustalić, panie Burmistrzu. Przy użyciu algorytmicznego czaru – " +
                    "zwanego przez wielkich ludzi algorytmem Edmondsa-Karpa obliczyliśmy, że:\n" + a1.getOutput()));
            speechList.set(10, new Pair<>('A', a2.getOutput()));
            speechList.set(13, new Pair<>('A', a3.getOutputList().get(0)));
            speechList.set(15, new Pair<>('A', "Dokładnie tak. Uaktualniliśmy pojemności źródeł w naszej sieci przepływu, a wyniki ponownie przeliczyliśmy. " +
                    "Okazało się, że po uwzględnieniu różnic regionalnych\n" + a3.getOutputList().get(1)));
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }


    }

    //wystarczy w odpowiedniej kolejnosci wpisac to co ktora posatc ma mowic
    private void fillSpeechList() {
        speechList.add(new Pair<>('S', "Doradco mój, od lat marzę, by karczmy naszej krainy pełne były złocistego trunku. " +
                "Teraz, gdy pola znów rodzą jęczmień, a browary dymią jak za dawnych czasów – przyszedł czas działać.\n"));
        speechList.add(new Pair<>('A', "W istocie, panie Burmistrzu. Zebrałeś już dane – znamy położenie pól, browarów i karczm. " +
                "Wiemy, ile jęczmienia rodzi się z każdego poletka. " +
                "Co więcej, dzięki mapie Bilba mamy dokładne informacje o drogach między skrzyżowaniami i o tym, " +
                "ile towaru da się nimi przewieźć. Brakuje jedynie… odpowiedniego planu.\n"));
        speechList.add(new Pair<>('S', "Otóż to! I tutaj na pomoc przychodzi dziwne urządzenie, " +
                "które Bilbo przywlókł z dalekich stron – komputer, jak to mówią duzi ludzie. " +
                "Czy moglibyśmy go użyć, by obliczyć... no, jak to się mówi… maksymalny przepływ?\n"));
        speechList.add(new Pair<>('A', "Tak, panie. Wprowadziliśmy dane do modelu sieci przepływów. " +
                "Każde pole traktujemy jak źródło jęczmienia, browary jako źródła piwa, a karczmy – jako ujścia trunku. " +
                "Drogi między skrzyżowaniami to krawędzie w grafie, z pojemnościami odpowiadającymi ilościom, " +
                "jakie można przetransportować.\n"));
        speechList.add(new Pair<>('S', "I ileż to piwa udało się dostarczyć do karczm?\n"));
        speechList.add(new Pair<>('A', ""));
        speechList.add(new Pair<>('S', "Doskonała robota! Ale słyszałem, że wiele dróg po rządach Sharkeya uległo zniszczeniu...\n"));
        speechList.add(new Pair<>('A', "Niestety tak, ale Peregrin i Meriadok zbadali już sprawę – " +
                "do każdej drogi przypisaliśmy koszt jej naprawy. Teraz nasz cel to: utrzymać ilość dostarczanego piwa, " +
                "ale przy jak najniższym koszcie napraw.\n"));
        speechList.add(new Pair<>('S', "I jak poradziliście sobie z tym zadaniem?\n"));
        speechList.add(new Pair<>('A', "Panie Burmistrzu, korzystając z nowego, bardziej złożonego algorytmu – " +
                "opracowanego przez najtęższe umysły Krain Ludzi – udało się wyznaczyć nie tylko największą możliwą ilość " +
                "jęczmienia i piwa, jaką możemy przetransportować… ale także zminimalizować koszty napraw sieci dróg, " +
                "którą można tego dokonać.\n"));
        speechList.add(new Pair<>('A', ""));
        speechList.add(new Pair<>('S', "Dobrze, dobrze… Ale co z ćwiartkami Shire? Peregrin opowiadał, że w jednej codziennie pada, a w innej susza, że aż ziemia pęka. " +
                "Mówił też, że różnie z nawozem — jedni mają najlepszy kompost, inni tylko popiół z ogniska… " +
                "Nie możemy przecież zakładać, że wszedzie rodzi sie tyle samo jęczmienia!\n"));
        speechList.add(new Pair<>('A', "I słusznie, panie Burmistrzu. Wzięliśmy to pod uwagę. " +
                "Dla każdej ćwiartki wyznaczyliśmy współczynnik urodzajności — zależny od pogody i nawożenia, o których wspominał Peregrin. " +
                "Na jego podstawie mnożymy bazową ilość jęczmienia, jaką rodzi każde pole, w zależności od tego, w której ćwiartce się znajduje.\n"));
        speechList.add(new Pair<>('A', ""));
        speechList.add(new Pair<>('S', "Czyli teraz każde pole daje tyle jęczmienia, ile wynika z jego ćwiartki?\n"));
        speechList.add(new Pair<>('A', ""));
        speechList.add(new Pair<>('S', "Dobrze zatem. Niech karczmy napełnią się śmiechem, kufle — piwem, a hobbici — spokojem." +
                " A my będziemy wiedzieć, że za tym wszystkim stoi porządny plan… i odrobina algorytmiki.\n"));
        this.startThread();
    }

    private void addResultToCompressedFile(){
        String joinedSpeech = speechList.stream()
                .map(Pair::getValue)
                .collect(Collectors.joining("\n"));

        if(Files.exists(Path.of("src/main/simulationResults/compressedResults.huf"))){
            HuffmanCoding.compress(
                    HuffmanCoding.decompress("src/main/simulationResults/compressedResults.huf")
                            + "\n" +
                            joinedSpeech,
                    "src/main/simulationResults/compressedResults.huf"
            );
        } else {
            HuffmanCoding.compress(joinedSpeech, "src/main/simulationResults/compressedResults.huf");
        }
    }

    private void skipHandler() {
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
    }

    @FXML
    public void initialize() {
        // Wypełnia listę dialogów
        fillSpeechList();

        root.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ESCAPE) {
                // ESC – szybki powrót do menu głównego, niezależnie od etapu dialogu
                SceneManager.switchScene("/fxml/main-menu.fxml", "/styles/main-menu.css");
            } else {
                // dowolny klawisz - przewin
                skipHandler();
            }
        });

        root.setOnMouseClicked(event -> {
            // LBM - pominiecie dialogu lub przejscie dalej
            skipHandler();
        });

        // Umożliwia reagowanie na klawiaturę zaraz po uruchomieniu widoku
        root.setFocusTraversable(true);
        Platform.runLater(() -> root.requestFocus());

        // Początkowa animacja sceny oraz pierwszego dialogu
        beginingAnimation();
    }

}

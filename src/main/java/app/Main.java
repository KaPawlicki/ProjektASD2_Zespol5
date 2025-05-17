package app;

import app.model.algorithm.text.Kmp;
import app.model.algorithm.text.RabinKarp;
import app.model.algorithm.text.huffman.HuffmanCoding;
import app.util.SceneManager;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

import static app.model.algorithm.text.Kmp.KMPSearch;


public class Main extends Application {

    @Override
    public void start(Stage stage) {
        SceneManager.setStage(stage);
        SceneManager.setTitle("BeerLand");
        SceneManager.setSize(1200, 800);
        SceneManager.setIcon("/images/icon.png");
        SceneManager.switchScene("/fxml/main-menu.fxml", "/styles/main-menu.css");
        //SceneManager.switchScene("/fxml/simulation-result-screen.fxml", "/styles/simulation-result-screen.css");
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        //launch();

//        List<Integer> test = RabinKarp.search("ala", "ala ma kota a kot ma ale ale ala nie ma ali a to kot ma ala ale nie alan tylko aaaaalaaaaaaaa kot");
//        System.out.println(test);
//        List<Integer> test2 = KMPSearch("ala", "ala ma kota a kot ma ale ale ala nie ma ali a to kot ma ala ale nie alan tylko aaaaalaaaaaaaa kot");
//        System.out.println(test2);
    }
}
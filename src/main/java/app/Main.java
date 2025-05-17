package app;

import app.model.algorithm.text.BoyerMoore;
import app.model.algorithm.text.Kmp;
import app.model.algorithm.text.huffman.HuffmanCoding;
import app.util.SceneManager;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;

import static app.model.algorithm.text.Kmp.KMPSearch;


public class Main extends Application {

    @Override
    public void start(Stage stage) {
        SceneManager.setStage(stage);
        SceneManager.setTitle("BeerLand");
        SceneManager.setSize(1200, 800);
        SceneManager.setIcon("/images/icon.png");

        //SceneManager.switchScene("/fxml/main-menu.fxml", "/styles/main-menu.css");
        SceneManager.switchScene("/fxml/searching-screen.fxml", "/styles/searching-screen.css");
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        launch();
    }
}
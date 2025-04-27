package app;

import app.model.*;
import app.util.DataLoader;
import app.util.DataWriter;
import app.util.SceneManager;
import javafx.application.Application;
import javafx.stage.Stage;
import java.awt.*;


public class Main extends Application {

    @Override
    public void start(Stage stage) {
        SceneManager.setStage(stage);
        SceneManager.setTitle("BeerLand");
        SceneManager.setSize(1200, 800);
        SceneManager.switchScene("/fxml/main-menu.fxml", "/styles/main-menu.css");
        //SceneManager.switchScene("/fxml/input-menu.fxml", "/styles/input-menu.css");
    }

    public static void main(String[] args) {

        //DOPOKI CHCECIE TESTOWAC SWOJE KLASY KONSOLOWO NIECH TO BEDZIE ZAKOMENTOWANE
        launch();

    }
}
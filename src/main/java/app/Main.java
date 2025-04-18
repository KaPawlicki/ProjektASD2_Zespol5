package app;

import app.util.SceneManager;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        SceneManager.setStage(stage);
        SceneManager.setTitle("BearLand");
        SceneManager.setIcon("/images/icon.png");
        SceneManager.setSize(1200, 800);
        SceneManager.switchScene("/fxml/main-menu.fxml", "/styles/main-menu.css");
    }

    public static void main(String[] args) {
        launch();
    }
}
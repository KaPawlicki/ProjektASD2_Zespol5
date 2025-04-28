package app.util;
import app.model.ShireMap;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.util.Objects;

public class SceneManager {

    private static Stage primaryStage;
    private static final ShireMap shireMap = new ShireMap(); // glowna wspoldzielona instancja mapy

    public static void setStage(Stage stage) {
        primaryStage = stage;
    }

    public static Stage getStage() {
        return primaryStage;
    }

    public static void setTitle(String title) {
        if (primaryStage != null) {
            primaryStage.setTitle(title);
        }
    }

    public static void setSize(double width, double height) {
        if (primaryStage != null) {
            primaryStage.setWidth(width);
            primaryStage.setHeight(height);
            primaryStage.setResizable(false);
        }
    }

    public static void setIcon(String icon) {
        if (primaryStage != null) {
            primaryStage.getIcons().add(new Image(Objects.requireNonNull(
                    SceneManager.class.getResourceAsStream(icon))));
        }
    }

    public static void switchScene(String fxmlPath, String cssPath) {;
        try {
            FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(
                    SceneManager.class.getResource(fxmlPath)));

            //przekazywanie instancji mapy do konstruktora kontrolera
            loader.setControllerFactory(type -> {
                try {
                    return type.getConstructor(ShireMap.class).newInstance(shireMap);
                } catch (Exception e) {
                    throw new RuntimeException("Nie można utworzyć kontrolera: " + type, e);
                }
            });

            Parent root = loader.load();
            Scene scene = new Scene(root);
            scene.getStylesheets().add(Objects.requireNonNull(
                    SceneManager.class.getResource(cssPath)).toExternalForm());
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            System.err.println("Nie można załadować pliku FXML: " + fxmlPath);
            e.printStackTrace();
        }
    }
}

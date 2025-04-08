package app.util;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.Objects;

public class SceneManager {

    private static Stage primaryStage;

    public static void setStage(Stage stage) {
        primaryStage = stage;
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
        }
    }

    public static void switchScene(String fxmlPath, String cssPath) {;
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(SceneManager.class.getResource(fxmlPath)));
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

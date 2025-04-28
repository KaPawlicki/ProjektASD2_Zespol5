package app;

import app.model.*;
import app.model.*;
import app.util.DataLoader;
import app.util.DataWriter;
import app.util.SceneManager;
import javafx.application.Application;
import javafx.stage.Stage;
import java.awt.*;


import java.awt.*;

public class Main extends Application {

    @Override
    public void start(Stage stage) {
        SceneManager.setStage(stage);
        SceneManager.setTitle("BeerLand");
        SceneManager.setSize(1200, 800);
        SceneManager.setIcon("/images/icon.png");
        SceneManager.switchScene("/fxml/main-menu.fxml", "/styles/main-menu.css");
    }

    public static void main(String[] args) {

        //launch();
        ShireMap shireMap = new ShireMap();

        Field field1 = new Field("Field1", new Point(0, 0), 20); // Główne źródło
        Field field2 = new Field("Field2", new Point(0, 10), 5); // Małe źródło

        Intersection inter = new Intersection("Inter", new Point(5, 5));
        Brewery brewery = new Brewery("Brewery", new Point(10, 5));
        Inn inn = new Inn("Inn", new Point(15, 5));

        shireMap.addNode(field1);
        shireMap.addNode(field2);
        shireMap.addNode(inter);
        shireMap.addNode(brewery);
        shireMap.addNode(inn);

        shireMap.addEdge(new Edge(field1.getId(), inter.getId(), 30, 1));
        shireMap.addEdge(new Edge(field2.getId(), brewery.getId(), 5, 1));
        shireMap.addEdge(new Edge(inter.getId(), brewery.getId(), 25, 1));
        shireMap.addEdge(new Edge(brewery.getId(), inn.getId(), 40, 1));

        shireMap.simulateWholeProcess();
        System.out.println("========");
        shireMap.simulateWholeProcessWithActivation();
    }
}
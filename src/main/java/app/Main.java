package app;

import app.model.*;
import app.util.SceneManager;
import javafx.application.Application;
import javafx.stage.Stage;
import java.awt.*;


public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        SceneManager.setStage(stage);
        SceneManager.setTitle("BeerLand");
        SceneManager.setSize(1200, 800);
        SceneManager.switchScene("/fxml/main-menu.fxml", "/styles/main-menu.css");
    }

    public static void main(String[] args) {

        //DOPOKI CHCECIE TESTOWAC SWOJE KLASY KONSOLOWO NIECH TO BEDZIE ZAKOMENTOWANE
        //launch();

        ShireMap shireMap = new ShireMap();
        Field field1 = new Field("Field1", new Point(0, 0), 20);
        Field field2 = new Field("Field2", new Point(10, 0), 30);

        Intersection intersection1 = new Intersection("Intersection1", new Point(5, 5));
        Intersection intersection2 = new Intersection("Intersection2", new Point(15, 5));
        Brewery brewery1 = new Brewery("Brewery1", new Point(5, 10));
        Brewery brewery2 = new Brewery("Brewery2", new Point(15, 10));
        Inn inn1 = new Inn("Inn1", new Point(5, 15));
        Inn inn2 = new Inn("Inn2", new Point(15, 15));
        shireMap.addNode(field1);
        shireMap.addNode(field2);
        shireMap.addNode(intersection1);
        shireMap.addNode(intersection2);
        shireMap.addNode(brewery1);
        shireMap.addNode(brewery2);
        shireMap.addNode(inn1);
        shireMap.addNode(inn2);
        shireMap.addEdge(new Edge(field1.getId(), intersection1.getId(), 15, 5));
        shireMap.addEdge(new Edge(field2.getId(), intersection2.getId(), 25, 5));
        shireMap.addEdge(new Edge(intersection1.getId(), brewery1.getId(), 10, 3));
        shireMap.addEdge(new Edge(intersection1.getId(), brewery2.getId(), 8, 7));
        shireMap.addEdge(new Edge(intersection2.getId(), brewery1.getId(), 5, 6));
        shireMap.addEdge(new Edge(intersection2.getId(), brewery2.getId(), 20, 4));
        shireMap.addEdge(new Edge(brewery1.getId(), intersection1.getId(), 100, 2));
        shireMap.addEdge(new Edge(brewery2.getId(), intersection2.getId(), 150, 3));
        shireMap.addEdge(new Edge(intersection1.getId(), inn1.getId(), 80, 4));
        shireMap.addEdge(new Edge(intersection1.getId(), inn2.getId(), 30, 8));
        shireMap.addEdge(new Edge(intersection2.getId(), inn1.getId(), 40, 5));
        shireMap.addEdge(new Edge(intersection2.getId(), inn2.getId(), 120, 3));
        shireMap.simulateWholeProcess();

    }
}
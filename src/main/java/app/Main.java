package app;

import app.model.algorithm.geometry.ConvexHull;
import app.model.structure.*;
import app.util.SceneManager;
import javafx.application.Application;
import javafx.stage.Stage;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

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
        launch();
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

        System.out.println("\n");

        List<Point> points = new ArrayList<>();

        /*
        points.add(new Point(2, 2));
        points.add(new Point(1, 3));
        points.add(new Point(3, 1));
        points.add(new Point(5, 2));
        points.add(new Point(4, 4));
        points.add(new Point(2, 5));
        points.add(new Point(1, 1));
        points.add(new Point(3, 3));
        points.add(new Point(5, 4));
        points.add(new Point(3, 5));
        */
        /*
        points.add(new Point(0, 3));
        points.add(new Point(1, 1));
        points.add(new Point(2, 2));
        points.add(new Point(4, 4));
        points.add(new Point(0, 0));
        points.add(new Point(1, 2));
        points.add(new Point(3, 1));
        points.add(new Point(3, 3));
        */
        points.add(new Point(0, 0));
        points.add(new Point(1, 0));
        points.add(new Point(2, 0));
        points.add(new Point(2, 1));
        points.add(new Point(4, 2));
        points.add(new Point(3, 4));
        points.add(new Point(1, 3));
        points.add(new Point(5, 1));
        points.add(new Point(3, 0));
        points.add(new Point(2, 2));
        ConvexHull otoczka = new ConvexHull(shireMap.getNodes());
        List<Point> punkty = otoczka.createConvexHull();
        for(Point p : punkty) {
            System.out.print(p + " ");
        }
    }
}
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
        launch();

        ShireMap map = new ShireMap();
        Node f1 = new Field("field", new Point(1, 2), 20);
        map.addNode(f1);
        Node f2 = new Field("field", new Point(1, 3), 20);
        map.addNode(f2);
        Node b1 = new Brewery("brewery", new Point(1, 4), 50);
        map.addNode(b1);
        Node i1 = new Inn("inn", new Point(1, 5));
        map.addNode(i1);
        map.print();
        map.addEdge(f1, new Edge(f1.getId(), f2.getId(), 10, 0));
        map.print();

    }
}
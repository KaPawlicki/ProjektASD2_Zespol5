package app.controller;

import app.model.structure.*;
import app.util.SceneManager;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.awt.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class InputMenuController {
    private ShireMap shireMap;
    private int nodeIndex;
    private final Map<Node, NodeListElementController> nodeControllerMap = new HashMap<>();
    private final Map<Node, EdgeListElementController> edgeControllerMap = new HashMap<>(); //Node z javafx

    @FXML
    private ScrollPane nodeListScrollBar;
    @FXML
    private VBox nodeList;
    @FXML
    private Button addNodeButton;
    @FXML
    private ScrollPane edgeListScrollBar;
    @FXML
    private VBox edgeList;
    @FXML
    private Button addEdgeButton;
    @FXML
    private AnchorPane root;

    public InputMenuController(ShireMap shireMap) {
        this.shireMap = shireMap;
    }

    @FXML
    public void initialize() {
        if (shireMap.isEmpty()) {
            nodeIndex = 0;
        } else {
            nodeIndex = shireMap.findMaxId() + 1;
            loadShireMapIntoGUI();
        }

        //obluga wcisniecia przycisku Escape
        root.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ESCAPE) {
                updateShireMap();
                SceneManager.switchScene("/fxml/main-menu.fxml", "/styles/main-menu.css");
            }
        });
        root.setFocusTraversable(true);
        root.requestFocus();


        //obsluga przycisku dodawania wierzcholkow
        addNodeButton.setOnAction(event -> {
            addNode();
        });

        //obsluga przycisku dodania krawedzi
        addEdgeButton.setOnAction(event -> {
            addEdge();
        });
    }

    public void updateShireMap() {
        shireMap.clear();
        //dodawanie wierzcholkow i krawedzi utowrzonych w GUI do ShireMap
        for(NodeListElementController controller : nodeControllerMap.values()) {
            int id = controller.getNodeIndex();
            String type = controller.getNodeType().getValue();
            int x = Integer.parseInt(controller.getX().getText());
            int y = Integer.parseInt(controller.getY().getText());

            switch(type){
                case "Pole":
                    int barleyAmount = Integer.parseInt(controller.getBarleyAmount().getText());
                    Field field = new Field(id, type, new Point(x,y), barleyAmount);
                    shireMap.addNode(field);
                    break;
                case "Browar":
                    Brewery brewery = new Brewery(id, type, new Point(x,y));
                    shireMap.addNode(brewery);
                    break;
                case "Karczma":
                    Inn inn = new Inn(id, type, new Point(x,y));
                    shireMap.addNode(inn);
                    break;
                case "Skrzyżowanie":
                    Intersection intersection = new Intersection(id, type, new Point(x,y));
                    shireMap.addNode(intersection);
                    break;
            }
        }

        for(EdgeListElementController controller : edgeControllerMap.values()) {
            int from = Integer.parseInt(controller.getFrom().getText());
            int to = Integer.parseInt(controller.getTo().getText());
            int capacity = Integer.parseInt(controller.getCapacity().getText());
            int repairCost = Integer.parseInt(controller.getRepairCost().getText());
            Edge edge = new Edge(from, to, capacity, repairCost);
            shireMap.addEdge(edge);
        }
    }

    public void addNode(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/node-list-element.fxml"));
            NodeListElementController controller = new NodeListElementController(nodeIndex); //dodanie do kontrolera numeru indexu
            loader.setController(controller);

            AnchorPane listItem = loader.load();
            nodeList.getChildren().add(listItem); //dodanie kafelka do listy w GUI
            nodeControllerMap.put(listItem, controller); //dodanie kontrolera elementu do mapy kontrolerow
            nodeIndex++;

            controller.setOnDeleteCallback(() -> {
                nodeControllerMap.remove(listItem); // usuwanie kontrolera powiązanego z tym GUI
            });

            //odswiezenie stylu i przesuniecie paska na dol listy
            nodeList.applyCss();
            nodeList.layout();
            Platform.runLater(() -> nodeListScrollBar.setVvalue(1.0));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void addEdge(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/edge-list-element.fxml"));
            EdgeListElementController controller = new EdgeListElementController();
            loader.setController(controller);

            AnchorPane listItem = loader.load();
            edgeList.getChildren().add(listItem); //dodanie kafelka do listy w GUI
            edgeControllerMap.put(listItem, controller); //dodanie kontrolera elementu do mapy kontrolerow

            controller.setOnDeleteCallback(() -> {
                edgeControllerMap.remove(listItem); // usuwanie kontrolera powiązanego z tym GUI
            });

            //odswiezenie stylu i przesuniecie paska na dol listy
            edgeList.applyCss();
            edgeList.layout();
            Platform.runLater(() -> edgeListScrollBar.setVvalue(1.0));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void loadShireMapIntoGUI(){
        for(app.model.structure.Node n : shireMap.getNodes().values()){
            int barleyAmount;
            if (n.getType().equals("Pole")) {
                Field field = (Field) n;
                barleyAmount = field.getBarleyAmount();
            } else {
                barleyAmount = 0;
            }

            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/node-list-element.fxml"));
                NodeListElementController controller = new NodeListElementController(n.getId(), n.getType(), n.getPosition().x, n.getPosition().y, barleyAmount);
                loader.setController(controller);

                AnchorPane listItem = loader.load();
                nodeList.getChildren().add(listItem);
                nodeControllerMap.put(listItem, controller);

                controller.setOnDeleteCallback(() -> {
                    nodeControllerMap.remove(listItem);
                });
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        for(app.model.structure.Node n : shireMap.getNodes().values()){
            for(Edge e : n.getOutgoingEdges()){
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/edge-list-element.fxml"));
                    EdgeListElementController controller = new EdgeListElementController(e.getFrom(), e.getTo(), e.getCapacity(), e.getRepairCost());
                    loader.setController(controller);

                    AnchorPane listItem = loader.load();
                    edgeList.getChildren().add(listItem);
                    edgeControllerMap.put(listItem, controller);

                    controller.setOnDeleteCallback(() -> {
                        edgeControllerMap.remove(listItem);
                    });
                } catch (IOException err) {
                    throw new RuntimeException(err);
                }
            }
        }
    }
}

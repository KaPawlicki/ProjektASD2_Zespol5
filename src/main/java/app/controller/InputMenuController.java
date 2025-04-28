package app.controller;

import app.model.ShireMap;
import app.util.SceneManager;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class InputMenuController {
    private ShireMap shireMap;
    private int nodeIndex = 1;

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
        //obluga wcisniecia przycisku Escape
        root.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ESCAPE) {
                SceneManager.switchScene("/fxml/main-menu.fxml", "/styles/main-menu.css");
            }
        });
        root.setFocusTraversable(true);
        root.requestFocus();

        //obsluga przycisku dodawania wierzcholkow
        addNodeButton.setOnAction(event -> {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/node-list-element.fxml"));
                NodeListElementController controller = new NodeListElementController(nodeIndex); //dodanie do kontrolera numeru indexu
                loader.setController(controller);

                AnchorPane listItem = loader.load();
                nodeList.getChildren().add(listItem);
                nodeIndex++;

                //odswiezenie stylu i przesuniecie paska na dol listy
                nodeList.applyCss();
                nodeList.layout();
                Platform.runLater(() -> nodeListScrollBar.setVvalue(1.0));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        addEdgeButton.setOnAction(event -> {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/edge-list-element.fxml"));
                AnchorPane listItem = loader.load();
                edgeList.getChildren().add(listItem);

                //odswiezenie stylu i przesuniecie paska na dol listy
                edgeList.applyCss();
                edgeList.layout();
                Platform.runLater(() -> edgeListScrollBar.setVvalue(1.0));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }
}

package app.controller;

import app.model.ShireMap;
import app.util.SceneManager;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class InputMenuController {
    public VBox nodeList;
    public Button addNodeButton;
    private ShireMap shireMap;

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
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/node-list-element.fxml"));
            try {
                AnchorPane listItem = loader.load();
                nodeList.getChildren().add(listItem);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }
}

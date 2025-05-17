package app.controller;

import app.model.structure.ShireMap;
import app.util.SceneManager;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;

public class SearchingScreenController {
    private ShireMap shireMap;

    @FXML
    private AnchorPane root;
    @FXML
    private Button exitButton;

    public SearchingScreenController(ShireMap shireMap) {
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

        exitButton.setOnAction(event -> {
            SceneManager.switchScene("/fxml/main-menu.fxml", "/styles/main-menu.css");
        });
    }
}

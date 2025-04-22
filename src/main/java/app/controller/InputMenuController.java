package app.controller;

import app.model.ShireMap;
import app.util.SceneManager;
import javafx.fxml.FXML;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;

public class InputMenuController {
    private ShireMap shireMap;

    @FXML
    private AnchorPane root;

    public InputMenuController(ShireMap shireMap) {
        this.shireMap = shireMap;
    }

    @FXML
    public void initialize() {
        root.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ESCAPE) {
                SceneManager.switchScene("/fxml/main-menu.fxml", "/styles/main-menu.css");
            }
        });
        root.setFocusTraversable(true);
        root.requestFocus();
    }
}

package app.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class MainMenuController {
    @FXML
    private Label text;

    @FXML
    protected void onButtonClick() {
        text.setText("testtest");
    }
}
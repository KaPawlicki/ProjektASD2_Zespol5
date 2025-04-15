package app.controller;

import app.model.Field;
import app.model.ShireMap;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.awt.*;

public class MainMenuController {

    private ShireMap shireMap;
    @FXML
    private Button startButton;
    @FXML
    private Button fromFileButton;
    @FXML
    private Button uploadManuallyButton;
    @FXML
    private Button fromPastScenarioButton;

    public MainMenuController(ShireMap shireMap) {
        this.shireMap = shireMap;
    }

    @FXML
    public void initialize() {
        startButton.setDisable(true);

        fromFileButton.setOnAction(event -> {
            startButton.setDisable(false);
        });

        uploadManuallyButton.setOnAction(event -> {
            startButton.setDisable(false);
        });

        fromPastScenarioButton.setOnAction(event -> {
            startButton.setDisable(false);
        });

//        startButton.setOnAction(event -> {
//            Field field1 = new Field("Field1", new Point(0, 0), 20);
//            Field field2 = new Field("Field2", new Point(10, 0), 30);
//            shireMap.addNode(field1);
//            shireMap.addNode(field2);
//
//            shireMap.print();
//
//        });
    }
}
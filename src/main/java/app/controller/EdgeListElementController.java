package app.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

public class EdgeListElementController {
    @FXML
    private AnchorPane root;
    @FXML
    private Button deleteButton;

    public void initialize() {
        //obsluga przycisku usun
        deleteButton.setOnAction(event -> {
            ((VBox) root.getParent()).getChildren().remove(root);
        });
    }
}

package app.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

public class NodeListElementController {
    private final int nodeIndex;

    @FXML
    private Label index;
    @FXML
    private Button deleteButton;
    @FXML
    private VBox barleyInputBox;
    @FXML
    private ComboBox<String> nodeType;
    @FXML
    private TextField x;
    @FXML
    private TextField y;
    @FXML
    private TextField barleyAmount;
    @FXML
    private AnchorPane root;

    public NodeListElementController(int nodeIndex) {
        this.nodeIndex = nodeIndex;
    }

    public void initialize() {
        //ustawianie indexu elementu i odpowiedniego stylu
        index.setText(String.valueOf(nodeIndex));

        if (nodeIndex > 9) {
            index.setStyle("-fx-padding: 0");
        }

        //sprawdzenie czy element listy jest typu Pole
        nodeType.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.equals("Pole")) {
                barleyInputBox.setStyle("-fx-opacity: 100");
            } else {
                barleyInputBox.setStyle("-fx-opacity: 0");
            }
        });

        //obsluga przycisku usun
        deleteButton.setOnAction(event -> {
            ((VBox) root.getParent()).getChildren().remove(root);
        });


    }
}

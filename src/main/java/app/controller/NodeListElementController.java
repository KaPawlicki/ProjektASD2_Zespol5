package app.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.util.ResourceBundle;

public class NodeListElementController {
    private final int nodeIndex;
    private String nodeTypeValue;
    private int xValue;
    private int yValue;
    private int barleyAmountValue;
    private Runnable onDeleteCallback;

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
        this.nodeTypeValue = "Pole";
        this.xValue = 0;
        this.yValue = 0;
        this.barleyAmountValue = 0;
    }

    public NodeListElementController(int nodeIndex, String nodeTypeValue, int xValue, int yValue, int barleyAmountValue) {
        this.nodeIndex = nodeIndex;
        this.nodeTypeValue = nodeTypeValue;
        this.xValue = xValue;
        this.yValue = yValue;
        this.barleyAmountValue = barleyAmountValue;
    }

    public void setOnDeleteCallback(Runnable onDeleteCallback) {
        this.onDeleteCallback = onDeleteCallback;
    }

    public int getNodeIndex() {
        return nodeIndex;
    }

    public TextField getX() {
        return x;
    }

    public TextField getY() {
        return y;
    }

    public ComboBox<String> getNodeType() {
        return nodeType;
    }

    public TextField getBarleyAmount() {
        return barleyAmount;
    }

    public void initialize() {
        //ustawianie wartosci elementow i odpowiedniego stylu
        index.setText(String.valueOf(nodeIndex));
        nodeType.setValue(nodeTypeValue);
        x.setText(String.valueOf(xValue));
        y.setText(String.valueOf(yValue));
        barleyAmount.setText(String.valueOf(barleyAmountValue));
        if (nodeTypeValue.equals("Pole")) {
            barleyInputBox.setStyle("-fx-opacity: 100");
        } else {
            barleyInputBox.setStyle("-fx-opacity: 0");
        }

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
            if (onDeleteCallback != null) {
                onDeleteCallback.run();
            }
        });


    }
}

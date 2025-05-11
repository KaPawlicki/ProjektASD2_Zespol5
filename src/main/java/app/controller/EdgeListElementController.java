package app.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

public class EdgeListElementController {
    private int fromValue;
    private int toValue;
    private int capacityValue;
    private int repairCostValue;
    private Runnable onDeleteCallback;
    @FXML
    private AnchorPane root;
    @FXML
    private Button deleteButton;
    @FXML
    private TextField from;
    @FXML
    private TextField to;
    @FXML
    private TextField capacity;
    @FXML
    private TextField repairCost;

    public TextField getFrom() {
        return from;
    }

    public TextField getTo() {
        return to;
    }

    public TextField getCapacity() {
        return capacity;
    }

    public TextField getRepairCost() {
        return repairCost;
    }

    public void setOnDeleteCallback(Runnable onDeleteCallback) {
        this.onDeleteCallback = onDeleteCallback;
    }

    public EdgeListElementController() {
        this.fromValue = 0;
        this.toValue = 0;
        this.capacityValue = 0;
        this.repairCostValue = 0;
    }

    public EdgeListElementController(int fromValue, int toValue, int capacityValue, int repairCostValue) {
        this.fromValue = fromValue;
        this.toValue = toValue;
        this.capacityValue = capacityValue;
        this.repairCostValue = repairCostValue;
    }

    public void initialize() {
        from.setText(fromValue + "");
        to.setText(toValue + "");
        capacity.setText(capacityValue + "");
        repairCost.setText(repairCostValue + "");

        //obsluga przycisku usun
        deleteButton.setOnAction(event -> {
            ((VBox) root.getParent()).getChildren().remove(root);
            if (onDeleteCallback != null) {
                onDeleteCallback.run();
            }
        });
    }
}

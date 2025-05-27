package app.model.structure;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import java.awt.*;

@Getter
@Setter
@ToString(callSuper = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Field extends Node {
    int barleyAmount;
    int currentAmountOfBarleyInTheField;

    public Field(int id, String type, Point position, int barleyAmount) {
        super(id, type, position);
        this.barleyAmount = barleyAmount;
        this.currentAmountOfBarleyInTheField = barleyAmount;
    }

    public void setCurrentAmountOfBarleyInTheField(int currentAmountOfBarleyInTheField) {
        this.currentAmountOfBarleyInTheField = Math.max(0, currentAmountOfBarleyInTheField);
    }

    public int harvest(int amount) {
        int harvested = Math.min(amount, currentAmountOfBarleyInTheField);
        currentAmountOfBarleyInTheField -= harvested;
        return harvested;
    }
}

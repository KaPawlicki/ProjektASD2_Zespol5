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
public class Inn extends Node {
    int amountOfBeer = 0;

    public Inn(int id, String type, Point position) {
        super(id, type, position);
    }

    public void setAmountOfBeer(int amountOfBeer) {
        this.amountOfBeer = Math.max(0, amountOfBeer);
    }

    public void receiveBeerFromBrewery(int amount) {
        this.amountOfBeer += amount;
    }
}

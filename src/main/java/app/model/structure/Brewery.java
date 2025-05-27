package app.model.structure;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import java.awt.*;

@Getter
@Setter
@ToString(callSuper = true)
public class Brewery extends Node {
    private static final int BEER_PER_TON_OF_BARLEY = 10;
    private int currentAmountOfBeerProduced = 0;

    public Brewery(int id, String type, Point position) {
        super(id, type, position);
    }

    public int processBarley(int availableBarley) {
        int producedBeer = availableBarley * BEER_PER_TON_OF_BARLEY;
        currentAmountOfBeerProduced += producedBeer;
        return producedBeer;
    }
}

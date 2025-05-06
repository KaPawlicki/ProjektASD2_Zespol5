package app.model.structure;

import java.awt.*;

public class Inn extends Node {
    private int amountOfBeer; // dostarczona ilość piwa do karczmy

    public Inn(String type, Point position) {
        super(type, position);
        this.amountOfBeer = 0;
    }

    public Inn(int id, String type, Point position) {
        super(id, type, position);
        this.amountOfBeer = 0;
    }

    public int getAmountOfBeer() {return amountOfBeer;}
    public void setAmountOfBeer(int amountOfBeer) {this.amountOfBeer = Math.max(0, amountOfBeer);}

    public void receiveBeerFromBrewery(int amount) {
        this.amountOfBeer += amount;
    }

    @Override
    public String toString() {
        return "Inn{" +
                "amountOfBeer=" + amountOfBeer +
                ", outgoingEdges=" + outgoingEdges +
                ", incomingEdges=" + incomingEdges +
                '}';
    }
}

package app.model;

import java.awt.*;

public class Inn extends Node{
    private int amountOfBeer; // dostarczona ilość piwa do karczmy

    public Inn(String type, Point position) {
        super(type, position);
        this.amountOfBeer = 0;
    }

    public int getAmountOfBeer() {return amountOfBeer;}
    public void setAmountOfBeer(int amountOfBeer) {this.amountOfBeer = amountOfBeer;}

    @Override
    public String toString() {
        String s = "id: " + getId() + ", type: " + getType() + ", position: " + getPosition() + ", amountOfBeer: " + amountOfBeer;
        if(listOfEdges.size()>0){
            s += "\n\tlistOfEdges: ";
            for(Edge e:listOfEdges){
                s += ", " + e;
            }
        }
        return s;
    }
}

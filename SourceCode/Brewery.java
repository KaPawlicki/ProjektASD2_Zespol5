package SourceCode;

import java.awt.*;

public class Brewery extends Node{
    private int amountOfBarleyProcessed; // ile dany browar może przetworzyć jęczmienia
    private int currentAmountOfBeerProduced; // aktualana ilość wyprodukowanego piwa

    public Brewery(String type, Point position, int amountOfBarleyProcessed) {
        super(type, position);
        this.amountOfBarleyProcessed = amountOfBarleyProcessed;
        this.currentAmountOfBeerProduced = 0;
    }

    public int getAmountOfBarleyProcessed() {return amountOfBarleyProcessed;}
    public void setAmountOfBarleyProcessed(int amountOfBarleyProcessed) {this.amountOfBarleyProcessed = amountOfBarleyProcessed;}
    public int getCurrentAmountOfBeerProduced() {return currentAmountOfBeerProduced;}
    public void setCurrentAmountOfBeerProduced(int currentAmountOfBeerProduced) {this.currentAmountOfBeerProduced = currentAmountOfBeerProduced;}

    @Override
    public String toString() {
        String s = "id: " + getId() + ", type: " + getType() + ", position: " + getPosition() + "amountOfBarleyProcessed: " + amountOfBarleyProcessed + ", currentAmountOfBeerProduced: " + currentAmountOfBeerProduced;
        if(listOfEdges.size()>0){
            s += "\n\tlistOfEdges: ";
            for(Edge e:listOfEdges){
                s += ", " + e;
            }
        }
        return s;
    }
}

